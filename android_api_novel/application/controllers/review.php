<?php
require('application/libraries/REST_Controller.php');
require APPPATH . 'libraries/Format.php';

class review extends REST_Controller {

   // show data review
   function user_get() {
       $get_transaksi = $this->db->query("SELECT pemb.id_review, pemb.id_user, pemb.isi_review,pemb.tanggal_review, pemb.id_buku FROM user, review pemb, buku Where pemb.id_user=user.id_user AND pemb.id_buku=buku.id_buku")->result();
     
       $this->response(array("status"=>"success","result" => $get_transaksi));
   }

   // insert review
   function user_post() {
       $data_review = array(
           'id_review'   => $this->post('id_review'),
           'id_user'     => $this->post('id_user'),
           'isi_review'    => $this->post('isi_review'),
           'tanggal_review'    => $this->post('tanggal_review'),
           'id_buku'       => $this->post('id_buku')
           );
      
       if  (empty($data_review['id_review'])){
            $this->response(array('status'=>'fail',"message"=>"id_review kosong"));
       }
       else {
           $getId = $this->db->query("Select id_review from review where id_review='".$data_review['id_review']."'")->result();
          
           //jika id_review tidak ada dalam database maka eksekusi insert
           if (empty($getId)){
                    if (empty($data_review['id_user'])){
                       $this->response(array('status'=>'fail',"message"=>"id_user kosong"));
                    }
                    else if(empty($data_review['isi_review'])){
                       $this->response(array('status'=>'fail',"message"=>"isi_review kosong"));
                    }else if(empty($data_review['id_buku'])){
                       $this->response(array('status'=>'fail',"message"=>"id_buku kosong"));
                    }else if(empty($data_review['tanggal_review'])){
                       $this->response(array('status'=>'fail',"message"=>"tanggal_review kosong"));
                    }
                    else{
                       //jika masuk pada else atau kondisi ini maka dipastikan seluruh input telah di set
                       //jika akan melakukan review id_user dan id_buku harus dipastikan ada
                       $getIduser = $this->db->query("Select id_user from user Where id_user='".$data_review['id_user']."'")->result();
                       $getIdbuku = $this->db->query("Select id_buku from buku Where id_buku='".$data_review['id_buku']."'")->result();
                       $message="";
                       if (empty($getIduser)) $message.="id_user tidak ada/salah ";
                       if (empty($getIdbuku)) {
                           if (empty($message)) {
                               $message.="id_buku tidak ada/salah";
                           }
                           else {
                               $message.="dan id_buku tidak ada/salah";
                           }
                       }
                       if (empty($message)){
                           $insert= $this->db->insert('review',$data_review);
                           if ($insert){
                               $this->response(array('status'=>'success','result' => $data_review,"message"=>$insert));   
                           }
                          
                       }else{
                           $this->response(array('status'=>'fail',"message"=>$message));   
                       }
                      
                    }
           }else{
               $this->response(array('status'=>'fail',"message"=>"id_review sudah ada"));
           }  
       }
   }

   // update data review
   function user_put() {
       $data_review = array(
                   'id_review'    => $this->put('id_review'),
                   'id_user'      => $this->put('id_user'),
                   'tanggal_review'    => $this->put('tanggal_review'),
                   'isi_review'     => $this->put('isi_review'),
                   'id_buku'        => $this->put('id_buku')
                   );
       if  (empty($data_review['id_review'])){
            $this->response(array('status'=>'fail',"message"=>"id_review kosong"));
       }else{
           $getId = $this->db->query("Select id_review from review where id_review='".$data_review['id_review']."'")->result();
           //jika id_review harus ada dalam database
           if (empty($getId)){
             $this->response(array('status'=>'fail',"message"=>"id_review tidak ada/salah")); 
           }else{
               //jika masuk disini maka dipastikan id_review ada dalam database
                if (empty($data_review['id_user'])){
                   $this->response(array('status'=>'fail',"message"=>"id_user kosong"));
                }
                else if(empty($data_review['isi_review'])){
                   $this->response(array('status'=>'fail',"message"=>"isi_review kosong"));
                }else if(empty($data_review['id_buku'])){
                   $this->response(array('status'=>'fail',"message"=>"id_buku kosong"));
                }else if(empty($data_review['tanggal_review'])){
                       $this->response(array('status'=>'fail',"message"=>"tanggal_review kosong"));
                } 
                else{
                   //jika masuk pada else atau kondisi ini maka dipastikan seluruh input telah di set
                   //jika akan melakukan edit review id_user dan id_buku harus dipastikan ada
                   $getIduser = $this->db->query("Select id_user from user Where id_user='".$data_review['id_user']."'")->result();
                       $getIdbuku = $this->db->query("Select id_buku from buku Where id_buku='".$data_review['id_buku']."'")->result();
                   $message="";
                   if (empty($getIduser)) $message.="id_user tidak ada/salah ";
                   if (empty($getIdbuku)) {
                       if (empty($message)) {
                           $message.="id_buku tidak ada/salah";
                       }
                       else {
                           $message.="dan id_buku tidak ada/salah";
                       }
                   }
                   if (empty($message)){
                       $this->db->where('id_review',$data_review['id_review']);
                       $update= $this->db->update('review',$data_review);
                       if ($update){
                           $this->response(array('status'=>'success','result' => $data_review,"message"=>$update));
                       }
                      
                   }else{
                       $this->response(array('status'=>'fail',"message"=>$message));   
                   }
                }
           }

       }
   }

   // delete review
   function user_delete() {
       $id_review = $this->delete('id_review');
       if (empty($id_review)){
           $this->response(array('status' => 'fail', "message"=>"id_review harus diisi"));
       } else {
           $this->db->where('id_review', $id_review);
           $delete = $this->db->delete('review');  
           if ($this->db->affected_rows()) {
               $this->response(array('status' => 'success','message' =>"Berhasil delete dengan id_review = ".$id_review));
           } else {
               $this->response(array('status' => 'fail', 'message' =>"id_review tidak dalam database"));
           }
       }
   }
}  