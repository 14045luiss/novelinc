# NovelNC
| Grup | 3 |
| :---------------: | :---------------:|
| Luis santiago | 18 |
| Dini Puspita sari | 07 |

## Deskripsi
Novel inc adalah aplikasi berbasis android yang terdiri dari 2 
 user dengan level yang berbeda yaitu admin dan user.Dimana antara 
 user tersebut mempunyai hak akses yang berbeda yaitu, admin dapat 
 melakukan CRUD(Create, Read, Update, dan Delete) pada data, Sedangkan 
 user hanya dapat melakukan Read data yang ada pada list dan detail dari data novel. 
 Untuk dapat masuk pada aplikasi tersebut user disediakan halaman login yang username dan 
 passwordnya berbeda antara admin dan user.Setelah admin login maka akan langsung masuk pada 
 halaman tampil data terbaru yang telah dilakukan perubahan baik create, update, maupun delete.
 Sedangkan user setelah login akan di arahkan ke menu list novel dimana pada list item tersebut 
 nantinya akan dapat melihat detail dari setiap itemnya.

## Screenshots
![Layar Home](http://echarlie.co/wp-content/uploads/salon-booking-app-hareesh-dribbble-salon-design-app.jpg)

## Android OS dan Level
min Android 4.0.3 (IceCreamSandwich)

## List class
### ADMIN

 - BukuAdapter.java
+ adalah class Adapter dari Buku
 - LayarDetail.java
+ digunakan untuk memanggil/ menggunakan fungsi pada ApiInterface.java review
 - LayarEditBuku.java
+ digunakan untuk memanggil/ menggunakan fungsi pada ApiInterface.java untuk edit buku
 - LayarInsertBuku.java
+ digunakan untuk memanggil/ menggunakan fungsi pada ApiInterface.java untuk insert buku
 - LayarListBuku.java
+ untuk layoutmanager recycleview dari tabel Review 
 - layarlogin.java
+ digunakan untuk mengeset login admin(validasi dll)
 - MainActivity.java
+ untuk layoutmanager recycleview dari tabel Review 
 - MyAdapter
+ adalah class adapater dari Review

+ MODEL :

 - Buku.java
+ digunakan untuk maping data pada tabel buku ke class Buku menggunakan Gson dan pembuatan Constructor set get method 
 - GetBuku.java
+ digunakan ketika get data buku
 - GetReview.java
+ digunakan ketika get data review
 - PostPutDelReview.java
+ digunakan ketika post,put,del data review
 - Review
+ digunakan untuk maping data pada tabel review ke class Review menggunakan Gson dan pembuatan Constructor set get method 

+ REST :

 - ApiClient.java 
+ digunakan untuk membuat Builder.Api yang berisi URL dari Rest Server
 - ApiInterface.java
+ digunakan untuk mengirim fungsi dan paramater ke server ketika melakukan komunikasi

### USER

 - BukuAdapter.java
+ adalah class Adapter dari Buku
 - LayarDetail.java
+ digunakan untuk memanggil/ menggunakan fungsi pada ApiInterface.java review
 - LayarListBuku.java
+ untuk layoutmanager recycleview dari tabel Review 
 - layarlogin.java
+ digunakan untuk mengeset login admin(validasi dll)
 - MainActivity.java
+ untuk layoutmanager recycleview dari tabel Review 
 - MyAdapter
+ adalah class adapater dari Review

+ MODEL :

 - Buku.java
+ digunakan untuk maping data pada tabel buku ke class Buku menggunakan Gson dan Pembuatan Constructor set get method 
 - GetBuku.java
+ digunakan ketika get data buku
 - GetReview.java
+ digunakan ketika get data review
 - PostPutDelReview.java
+ digunakan ketika post,put,del data review
 - Review
+ digunakan untuk maping data pada tabel review ke class Review menggunakan Gson dan Pembuatan Constructor set get method 

+ REST :

 - ApiClient.java 
+ digunakan untuk membuat Builder.Api yang berisi URL dari Rest Server
 - ApiInterface.java
+ digunakan untuk mengirim fungsi dan paramater ke server ketika melakukan komunikasi



## Referensi
+ Android developer fundamentals course concepths idn (Dikembangkan oleh Tim Pelatihan Developer Google
Terakhir diperbarui: Desember 2016)
