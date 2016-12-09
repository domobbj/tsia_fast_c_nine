<?php

/**
 * This is the model class for table "_User".
 *
 * The followings are the available columns in table '_User':
 * @property string $username
 * @property string $password
 * @property string $mobilePhoneNumber
 * @property string $userNickName
 * @property string $userWork
 * @property integer $userRole
 * @property string $departmentId
 * @property string $seatPart
 * @property string $seatId
 * @property string $userId
 * @property string $userTag
 * @property string $userPos
 * @property string $userIcon
 */
class User extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return User the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return '_User';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('mobilePhoneNumber, userNickName, userWork, userRole, departmentId, seatPart, seatId, userId, userTag, userPos, userIcon', 'required'),
			array('userRole', 'numerical', 'integerOnly'=>true),
			array('username, userNickName, userWork, userTag, userPos', 'length', 'max'=>255),
			array('password', 'length', 'max'=>24),
			array('mobilePhoneNumber', 'length', 'max'=>13),
			array('departmentId', 'length', 'max'=>20),
			array('seatPart, seatId', 'length', 'max'=>4),
			array('userId', 'length', 'max'=>12),
			array('userIcon', 'length', 'max'=>2048),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('username, password, mobilePhoneNumber, userNickName, userWork, userRole, departmentId, seatPart, seatId, userId, userTag, userPos, userIcon', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'username' => 'Username',
			'password' => 'Password',
			'mobilePhoneNumber' => 'Mobile Phone Number',
			'userNickName' => 'User Nick Name',
			'userWork' => 'User Work',
			'userRole' => 'User Role',
			'departmentId' => 'Department',
			'seatPart' => 'Seat Part',
			'seatId' => 'Seat',
			'userId' => 'User',
			'userTag' => 'User Tag',
			'userPos' => 'User Pos',
			'userIcon' => 'User Icon',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		// Warning: Please modify the following code to remove attributes that
		// should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('username',$this->username,true);
		$criteria->compare('password',$this->password,true);
		$criteria->compare('mobilePhoneNumber',$this->mobilePhoneNumber,true);
		$criteria->compare('userNickName',$this->userNickName,true);
		$criteria->compare('userWork',$this->userWork,true);
		$criteria->compare('userRole',$this->userRole);
		$criteria->compare('departmentId',$this->departmentId,true);
		$criteria->compare('seatPart',$this->seatPart,true);
		$criteria->compare('seatId',$this->seatId,true);
		$criteria->compare('userId',$this->userId,true);
		$criteria->compare('userTag',$this->userTag,true);
		$criteria->compare('userPos',$this->userPos,true);
		$criteria->compare('userIcon',$this->userIcon,true);

		return new CActiveDataProvider(get_class($this), array(
			'criteria'=>$criteria,
		));
	}
}