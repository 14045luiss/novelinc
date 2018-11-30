<?php
require('application/libraries/REST_Controller.php');
require APPPATH . 'libraries/Format.php';

class user extends REST_Controller {

   // show data user
   function user_get() {
       $get_user = $this->db->query("SELECT id_user,username,password,level from user")->result();
     
       $this->response(array("status"=>"success","result" => $get_user));
   }

   // insert user
   function user_post() {
       $data_user = array(
           'id_user'     => $this->post('id_user'),
           'username'    => $this->post('username'),
           'password'    => $this->post('password'),
           'level'       => $this->post('level')
           );
      
       if  (empty($data_user['id_user'])){
            $this->response(array('status'=>'fail',"message"=>"id_user kosong"));
       }
       else {
           $getId = $this->db->query("Select id_user from user where id_user='".$data_user['id_user']."'")->result();
          
           //jika id_user tidak ada dalam database maka eksekusi insert
           if (empty($getId)){
                    if (empty($data_user['id_user'])){
                       $this->response(array('status'=>'fail',"message"=>"id_user kosong"));
                    }
                    else if(empty($data_user['username'])){
                       $this->response(array('status'=>'fail',"message"=>"username kosong"));
                    }else if(empty($data_user['level'])){
                       $this->response(array('status'=>'fail',"message"=>"level kosong"));
                    }else if(empty($data_user['password'])){
                       $this->response(array('status'=>'fail',"message"=>"password kosong"));
                    }
                    else{
                       //jika masuk pada else atau kondisi ini maka dipastikan seluruh input telah di set
                       //jika akan melakukan user id_user dan level harus dipastikan ada
                       $getIduser = $this->db->query("Select id_user from user Where id_user='".$data_user['id_user']."'")->result();
                       $message="";
                       if (empty($getIduser)) $message.="id_user tidak ada/salah ";
                       if (empty($message)){
                           $insert= $this->db->insert('user',$data_user);
                           if ($insert){
                               $this->response(array('status'=>'success','result' => $data_user,"message"=>$insert));   
                           }
                          
                       }else{
                           $this->response(array('status'=>'fail',"message"=>$message));   
                       }
                      
                    }
           }else{
               $this->response(array('status'=>'fail',"message"=>"id_user sudah ada"));
           }  
       }
   }

   // update data user
   function user_put() {
       $data_user = array(
                   'id_user'      => $this->put('id_user'),
                   'password'    => $this->put('password'),
                   'username'     => $this->put('username'),
                   'level'        => $this->put('level')
                   );
       if  (empty($data_user['id_user'])){
            $this->response(array('status'=>'fail',"message"=>"id_user kosong"));
       }else{
           $getId = $this->db->query("Select id_user from user where id_user='".$data_user['id_user']."'")->result();
           //jika id_user harus ada dalam database
           if (empty($getId)){
             $this->response(array('status'=>'fail',"message"=>"id_user tidak ada/salah")); 
           }else{
               //jika masuk disini maka dipastikan id_user ada dalam database
                if (empty($data_user['id_user'])){
                   $this->response(array('status'=>'fail',"message"=>"id_user kosong"));
                }
                else if(empty($data_user['username'])){
                   $this->response(array('status'=>'fail',"message"=>"username kosong"));
                }else if(empty($data_user['password'])){
                       $this->response(array('status'=>'fail',"message"=>"password kosong"));
                } 
                else{
                   //jika masuk pada else atau kondisi ini maka dipastikan seluruh input telah di set
                   //jika akan melakukan edit user id_user dan level harus dipastikan ada
                   $getIduser = $this->db->query("Select id_user from user Where id_user='".$data_user['id_user']."'")->result();
                   $message="";
                   if (empty($getIduser)) $message.="id_user tidak ada/salah ";
                   if (empty($message)){
                       $this->db->where('id_user',$data_user['id_user']);
                       $update= $this->db->update('user',$data_user);
                       if ($update){
                           $this->response(array('status'=>'success','result' => $data_user,"message"=>$update));
                       }
                      
                   }else{
                       $this->response(array('status'=>'fail',"message"=>$message));   
                   }
                }
           }

       }
   }

   // delete user
   function user_delete() {
       $id_user = $this->delete('id_user');
       if (empty($id_user)){
           $this->response(array('status' => 'fail', "message"=>"id_user harus diisi"));
       } else {
           $this->db->where('id_user', $id_user);
           $delete = $this->db->delete('user');  
           if ($this->db->affected_rows()) {
               $this->response(array('status' => 'success','message' =>"Berhasil delete dengan id_user = ".$id_user));
           } else {
               $this->response(array('status' => 'fail', 'message' =>"id_user tidak dalam database"));
           }
       }
   }
}  