#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
统计上一个小时出现次数最多的几个关键字
"""

import datetime
import logging
import time

from django.conf import settings
from django.db import connection

from db.models import HotSelectHourly


class HotSelectStatsHourly(object):
    def __init__(self, domobtime):
        self.logger = logging.getLogger('domob.stats')
        self.dt = int((domobtime + datetime.timedelta(hours=-1)).strftime('%Y%m%d'))
        self.hr = int((domobtime + datetime.timedelta(hours=-1)).strftime('%H'))

    def get_select_count(self):
        """
        统计出现次数最多的几个关键词
        """
        self.logger.debug("start get_select_count >>>")
        sql = '''SELECT `key`, COUNT(id) num FROM hot_select WHERE dt={dt} AND
        hr={hr} GROUP BY `key` ORDER BY num DESC LIMIT {max}'''.format(dt=self.dt, hr=self.hr, max=settings.MAX_HOT_NUM)
        cursor = connection.cursor()
        cursor.execute(sql)
        records = cursor.fetchall()
        dicts = {}
        for record in records:
            dicts[record[0]] = record[1]
        self.logger.info("get %d records from db" % len(dicts))
        return dicts

    def run(self):
        self.logger.debug("start run >>>")
        hot_select = self.get_select_count()
        # 支持重跑 删除老数据
        HotSelectHourly.objects.filter(dt=self.dt, hr=self.hr).delete()
        self.logger.info("delete all old datas")
        for key, value in hot_select.items():
            self.logger.info("key word is:%s" % key)
            o = HotSelectHourly()
            o.key = key
            o.create_time = int(time.time())
            o.dt = self.dt
            o.hr = self.hr
            o.num = value
            try:
                o.save()
            except Exception, e:
                self.logger.error(e)
