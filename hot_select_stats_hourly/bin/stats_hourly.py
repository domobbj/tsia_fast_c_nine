#! /usr/local/bin/python
# -*- coding: utf-8 -*-

"""
@关键词小时级统计
"""

import argparse
import datetime
import logging.config
import os
import sys
import time

basepath = os.path.realpath(os.path.dirname(__file__) + '/../')
sys.path.append(basepath + '/lib')

if __name__ == '__main__':
    ap = argparse.ArgumentParser(description='tsia hot select stats hourly')
    ap.add_argument('-d', '--executeDir', type=str,
                    help='execute directory, prog will find conf in this dir',
                    default=basepath)
    ap.add_argument('-t', '--dominotime', type=str,
                    help='dominotime, at which hour this prog will stat, format %Y-%m-%d %H:%M:%S',
                    default=time.strftime('%Y-%m-%d %H:%M:%S'))

    args = ap.parse_args()
    print 'run at %s' % args.executeDir
    os.chdir(args.executeDir)

    logConfFile = args.executeDir + '/conf/logging.conf'
    logging.config.fileConfig(logConfFile)

    sys.path.append('./conf')
    os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'django_settings')

    from hot_select_stats_hourly import HotSelectStatsHourly

    dominotime = datetime.datetime.strptime(args.dominotime, '%Y-%m-%d %H:%M:%S')
    HotSelectStatsHourly(dominotime).run()
