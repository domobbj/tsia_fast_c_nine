<?php
class MapController extends Controller {
    
    public $layout = false;
    
    public function actionIndex($id='1000') {
        $model = new SeatModel();
        $seat = $model->getSeatId($id);
        if (!$seat) {
            $this->render('error');
            return ;
        }
        $this->render('index', 
            array(
                'model'=>$model,
                'seat'=>$seat,
                'my'=>'C32',
                
            )
        );
    }
    
    public function actionGetUserInfo() {
        $model = new SeatModel();
        $user = $model->getUserInfo($_GET['id']);
        echo json_encode($user);
    }
    
    public function actionGetSeat() {
        $model = new SeatModel();
        echo json_encode($model->seat);
    }

}
