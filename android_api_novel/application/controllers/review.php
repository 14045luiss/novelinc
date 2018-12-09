<?php

// use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

// Jika ada pesan "REST_Controller not found"
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Review extends REST_Controller {

    // Konfigurasi letak folder untuk upload image
    private $folder_upload = 'uploads/';

    function all_get(){
         $get_review = $this->db->query("SELECT pemb.id_review,pemb.id_user,pemb.isi_review,pemb.tanggal_review,pemb.id_buku FROM user, review pemb, buku Where pemb.id_user=user.id_user AND pemb.id_buku=buku.id_buku")->result();
        $this->response(array(
            "status"=>"success",
            "result"=>$get_review
        ));
    }

    function all_post() {

        $action  = $this->post('action');
        $data_buku = array(
                       'id_review' => $this->post('id_review'),
                       'id_user'       => $this->post('id_user'),
                       'id_buku'     => $this->post('id_buku'),
                       'isi_review'      => $this->post('isi_review'),
                       'tanggal_review'     => $this->post('tanggal_review'));

        switch ($action) {
            case 'insert':
                $this->insertReview($data_review);
                break;
            
            case 'update':
                $this->updateReview($data_review);
                break;
            
            case 'delete':
                $this->deleteReview($data_review);
                break;
            
            default:
                $this->response(
                    array(
                        "status"  =>"failed",
                        "message" => "action harus diisi"
                    )
                );
                break;
        }
    }

    function insertReview($data_review){

       // Cek validasi
    if (empty($data_review['id_user']) || empty($data_review['isi_review']) || empty($data_review['tanggal_review']) || empty($data_review['id_buku'])){
           $this->response(
               array(
                   "status" => "failed",
                   "message" => "Id User / isi review / tanggal review / id buku"
               )
           );
       } else {

           $data_buku['photo_url'] = $this->uploadPhoto();

           $do_insert = $this->db->insert('review', $data_review);
          
           if ($do_insert){
               $this->response(
                   array(
                       "status" => "success",
                       "result" => array($data_review),
                       "message" => $do_insert
                   )
               );
            }
       }
    }

    function updateBuku($data_buku){

       // Cek validasi
        if (empty($data_review['id_user']) || empty($data_review['isi_review']) || empty($data_review['tanggal_review']) || empty($data_review['id_buku'])){
           $this->response(
               array(
                   "status" => "failed",
                   "message" => "Id User / isi review / tanggal review / id buku"
               )
           );
       } else {
           // Cek apakah ada di database
           $get_buku_baseID = $this->db->query("
               SELECT 1
               FROM review
               WHERE id_review =  {$data_review['id_review']}")->num_rows();

           if($get_review_baseID === 0){
               // Jika tidak ada
               $this->response(

                   array(
                       "status"  => "failed",
                       "message" => "ID Review tidak ditemukan"
                   )
               );
           } else {
               // Jika ada
               $data_buku['photo_url'] = $this->uploadPhoto();

               if ($data_buku['photo_url']){
                   // Jika upload foto berhasil, eksekusi update
                   $update = $this->db->query("
                       UPDATE buku SET
                           id_user = '{$data_review['id_user']}',
                           isi_review = '{$data_review['isi_review']}',
                           tanggal_review = '{$data_review['tanggal_review']}',
                           id_buku = '{$data_review['id_buku']}',
                           photo_url = '{$data_review['photo_url']}'
                       WHERE id_review = '{$data_review['id_review']}'");

               } else {
                   // Jika foto kosong atau upload foto tidak berhasil, eksekusi update
                    $update = $this->db->query("
                        UPDATE review
                        SET
                            id_user    = '{$data_review['id_user']}',
                            isi_review  = '{$data_review['isi_review']}',
                            tanggal_review = '{$data_review['tanggal_review']}',
                            id_buku = '{$data_review['id_buku']}',
                            photo_url = '{$data_review['photo_url']}'
                        WHERE id_review = {$data_review['id_review']}"
                    );
               }
              
               if ($update){
                   $this->response(
                       array(
                           "status"    => "success",
                           "result"    => array($data_review),
                           "message"   => $update
                       )
                   );
                }
           }   
       }
    }

    function deleteBuku($data_review){

        if (empty($data_review['id_review'])){
           $this->response(
               array(
                   "status" => "failed",
                   "message" => "ID Review harus diisi"
               )
           );
       } else {
           // Cek apakah ada di database
           $get_review_baseID =$this->db->query("
               SELECT 1
               FROM buku
               WHERE id_review= {$data_review['id_review']}")->num_rows();

           if($get_buku_baseID > 0){
               
               $get_photo_url =$this->db->query("
               SELECT photo_url
               FROM review
               WHERE id_review= {$data_review['id_review']}")->result();
           
                if(!empty($get_photo_url)){

                    // Dapatkan nama file
                    $photo_nama_file = basename($get_photo_url[0]->photo_url);
                    // Dapatkan letak file di folder upload
                    $photo_lokasi_file = realpath(FCPATH . $this->folder_upload . $photo_nama_file);
                    
                    // Jika file ada, hapus
                    if(file_exists($photo_lokasi_file)) {
                        // Hapus file
                       unlink($photo_lokasi_file);
                   }

                   $this->db->query("
                       DELETE FROM review
                       WHERE id_review = {$data_review['id_review']}");
                   $this->response(
                       array(
                           "status" => "success",
                           "message" => "Data ID = " .$data_review['id_review']. " berhasil dihapus"
                       )
                   );
               }
           
            } else {
                $this->response(
                    array(
                        "status" => "failed",
                        "message" => "ID review tidak ditemukan"
                    )
                );
            }
       }
    }

    function uploadPhoto() {

        // Apakah user upload gambar?
        if ( isset($_FILES['photo_url']) && $_FILES['photo_url']['size'] > 0 ){

            // Foto disimpan di android-api/uploads
            $config['upload_path'] = realpath(FCPATH . $this->folder_upload);
            $config['allowed_types'] = 'jpg|png';

           // Load library upload & helper
           $this->load->library('upload', $config);
           $this->load->helper('url');

           // Apakah file berhasil diupload?
           if ( $this->upload->do_upload('photo_url')) {

               // Berhasil, simpan nama file-nya
               // URL image yang disimpan adalah http://localhost/android-api/uploads/namafile
               $img_data = $this->upload->data();
               $post_image = base_url(). $this->folder_upload .$img_data['file_name'];

           } else {

               // Upload gagal, beri nama image dengan errornya
               // Ini bodoh, tapi efektif
               $post_image = $this->upload->display_errors();
               
           }
       } else {
           // Tidak ada file yang di-upload, kosongkan nama image-nya
           $post_image = '';
       }

       return $post_image;
    }
}
