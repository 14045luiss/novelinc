<?php

// use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

// Jika ada pesan "REST_Controller not found"
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Buku extends REST_Controller {

    // Konfigurasi letak folder untuk upload image
    private $folder_upload = 'uploads/';

    function all_get(){
        $get_buku = $this->db->query("
            SELECT
                id_buku,
                judul,
                penulis,
                tahun_terbit,
                penerbit,
                sinopsis,
                photo_url

FROM buku")->result();
       $this->response(
           array(
               "status" => "success",
               "result" => $get_buku
           )
       );
    }

    function all_post() {

        $action  = $this->post('action');
        $data_buku = array(
                       'id_buku' => $this->post('id_buku'),
                       'judul'       => $this->post('judul'),
                       'penulis'     => $this->post('penulis'),
                       'tahun_terbit'      => $this->post('tahun_terbit'),
                       'penerbit'     => $this->post('penerbit'),
                       'sinopsis'      => $this->post('sinopsis'),
                       'photo_url'   => $this->post('photo_url')
                   );

        switch ($action) {
            case 'insert':
                $this->insertBuku($data_buku);
                break;
            
            case 'update':
                $this->updateBuku($data_buku);
                break;
            
            case 'delete':
                $this->deleteBuku($data_buku);
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

    function insertBuku($data_buku){

       // Cek validasi
    if (empty($data_buku['judul']) || empty($data_buku['penulis']) || empty($data_buku['tahun_terbit']) || empty($data_buku['penerbit']) || empty($data_buku['sinopsis'])){
           $this->response(
               array(
                   "status" => "failed",
                   "message" => "Judul / penulis / tahun terbit / penerbit / sinopsis harus diisi"
               )
           );
       } else {

           $data_buku['photo_url'] = $this->uploadPhoto();

           $do_insert = $this->db->insert('buku', $data_buku);
          
           if ($do_insert){
               $this->response(
                   array(
                       "status" => "success",
                       "result" => array($data_buku),
                       "message" => $do_insert
                   )
               );
            }
       }
    }

    function updateBuku($data_buku){

       // Cek validasi
       if (empty($data_buku['judul']) || empty($data_buku['penulis']) || empty($data_buku['tahun_terbit']) || empty($data_buku['penerbit']) || empty($data_buku['sinopsis'])){
           $this->response(
               array(
                   "status" => "failed",
                   "message" => "Judul / penulis / tahun terbit / penerbit / sinopsis harus diisi"
               )
           );
       } else {
           // Cek apakah ada di database
           $get_buku_baseID = $this->db->query("
               SELECT 1
               FROM buku
               WHERE id_buku =  {$data_buku['id_buku']}")->num_rows();

           if($get_buku_baseID === 0){
               // Jika tidak ada
               $this->response(

                   array(
                       "status"  => "failed",
                       "message" => "ID Buku tidak ditemukan"
                   )
               );
           } else {
               // Jika ada
               $data_buku['photo_url'] = $this->uploadPhoto();

               if ($data_buku['photo_url']){
                   // Jika upload foto berhasil, eksekusi update
                   $update = $this->db->query("
                       UPDATE buku SET
                           judul = '{$data_buku['judul']}',
                           penulis = '{$data_buku['penulis']}',
                           tahun_terbit = '{$data_buku['tahun_terbit']}',
                           penerbit = '{$data_buku['penerbit']}',
                           sinopsis = '{$data_buku['sinopsis']}',
                           photo_url = '{$data_buku['photo_url']}'
                       WHERE id_buku = '{$data_buku['id_buku']}'");

               } else {
                   // Jika foto kosong atau upload foto tidak berhasil, eksekusi update
                    $update = $this->db->query("
                        UPDATE buku
                        SET
                            judul    = '{$data_buku['judul']}',
                            penulis  = '{$data_buku['penulis']}',
                            tahun_terbit = '{$data_buku['tahun_terbit']}',
                            penerbit = '{$data_buku['penerbit']}',
                            sinopsis = '{$data_buku['sinopsis']}',
                            photo_url = '{$data_buku['photo_url']}'
                        WHERE id_buku = {$data_buku['id_buku']}"
                    );
               }
              
               if ($update){
                   $this->response(
                       array(
                           "status"    => "success",
                           "result"    => array($data_buku),
                           "message"   => $update
                       )
                   );
                }
           }   
       }
    }

    function deleteBuku($data_buku){

        if (empty($data_buku['id_buku'])){
           $this->response(
               array(
                   "status" => "failed",
                   "message" => "ID Buku harus diisi"
               )
           );
       } else {
           // Cek apakah ada di database
           $get_buku_baseID =$this->db->query("
               SELECT 1
               FROM buku
               WHERE id_buku = {$data_buku['id_buku']}")->num_rows();

           if($get_buku_baseID > 0){
               
               $get_photo_url =$this->db->query("
               SELECT photo_url
               FROM buku
               WHERE id_buku = {$data_buku['id_buku']}")->result();
           
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
                       DELETE FROM buku
                       WHERE id_buku = {$data_buku['id_buku']}");
                   $this->response(
                       array(
                           "status" => "success",
                           "message" => "Data ID = " .$data_buku['id_buku']. " berhasil dihapus"
                       )
                   );
               }
           
            } else {
                $this->response(
                    array(
                        "status" => "failed",
                        "message" => "ID Buku tidak ditemukan"
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
