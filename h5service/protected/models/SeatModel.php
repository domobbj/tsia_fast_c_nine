<?php 
class SeatModel extends User {
    public $id;
    public $seat = [
        'A'=>[
            1=>[1,2,3,4],
            2=>[1,2,3,4],
            3=>[1,2,3,4],
            4=>[1,2,3,4],
            5=>[1,2,3,4],
            6=>[1,2,3,4],
        ],
        'B'=>[
            1=>[1,2,3,4],
            2=>[1,2,3,4],
            3=>[1,2,3,4],
        ],
        'C'=>[
            1 => [1,2,3,4,5,6,7,8,9,10],
            2 => [1,2,3,4,5,6,7,8,9,10],
            3 => [1,2,3,4,5,6,7,8,9,10],
            4 => [1,2,3,4,5],
        ],
        'D'=>[
            1=>[1,2,3,4,5,6,7,8],
            2=>[1,2,3,4,5,6,7,8],
            3=>[1,2,3,4,5,6,7,8],
            4=>[1,2,3,4,5,6,7,8],
            5=>[1,2,3,4,5,6,7,8],
            6=>[1,2,3,4,5,6,7,8],
            7=>[1,2,3,4,5,6,7,8],
        ],
        'E'=>[
            1=>[1,2,3,4,5,6,7,8],
            2=>[1,2,3,4,5,6,7,8],
            3=>[1,2,3,4,5,6,7,8],
            4=>[1,2,3,4,5,6,7,8],
            5=>[1,2,3,4,5,6,7,8],
            6=>[1,2,3,4,5,6,7,8],
            7=>[1,2,3,4,5,6,7,8],
        ],
    ];
    public function getSeatId($id) {
        $record = User::model()->findByPk($id);
        if (!$record || !$record->seatPart || !$record->seatId) {
            return false;
        } else {
            return $record->seatPart.$record->seatId;
        }
    }
    
    public function getUserInfo($id) {
        $part = preg_split("/([A-Z]+)/", $id, 0, PREG_SPLIT_NO_EMPTY | PREG_SPLIT_DELIM_CAPTURE);    
        $record = User::model()->find(
            array(
                'condition'=>'seatPart="'.$part[0].'" and seatId = '.$part[1]
            ));
        if (!$record) {
            return false;
        } else {
            return $record->attributes;
        }
    }
}

?>