package com.akmalin.sasahurfoods.data.datasource.menu

import com.akmalin.sasahurfoods.data.model.Menu

class DummyMenuDataSource: MenuDataSource {
    override fun getMenus(): List<Menu> {
        return mutableListOf(
            Menu(
                imgUrl = "https://raw.githubusercontent.com/akmalinnn/sasahursFood-assets/main/product_img/img_ayam.webp",
                name = "Penyetan Ayam",
                price = 15000.0,
                desc = "Nikmati sensasi pedas yang menggoda dan kelezatan ayam yang digoreng dengan sempurna dalam hidangan Penyetan Ayam kami. Setiap gigitan akan membawa Anda merasakan krispinya kulit ayam yang gurih dilapisi dengan sambal pedas yang membara. Disajikan dengan nasi hangat, lalapan segar, dan sambal tambahan sesuai selera Anda. Dijamin membuat lidah Anda bergoyang!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
                rating = 5.0
            ),
            Menu(
                imgUrl = "https://raw.githubusercontent.com/akmalinnn/sasahursFood-assets/main/product_img/img_ayam_komplit.webp",
                name = "Penyetan Komplit",
                price = 20000.0,
                desc = "Dalam hidangan Penyetan Komplit kami, Anda tidak hanya mendapatkan kenikmatan dari ayam yang digoreng dengan sempurna, tetapi juga sensasi dari tahu, tempe, dan lalapan segar yang menyertainya. Nikmati perpaduan rasa yang harmonis dari berbagai bahan yang diolah dengan cermat oleh koki kami. Setiap suapannya akan memanjakan lidah Anda dengan cita rasa yang tak terlupakan!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
                rating = 5.0
            ),
            Menu(
                imgUrl = "https://raw.githubusercontent.com/akmalinnn/sasahursFood-assets/main/product_img/img_lele.webp",
                name = "Lele Goreng",
                price = 15000.0,
                desc = "Siapkan lidah Anda untuk pengalaman gurih yang luar biasa dengan Lele Goreng kami. Lele yang digoreng dengan tepung crispy, disajikan dengan sambal spesial yang memikat. Setiap gigitan akan membawa Anda merasakan kombinasi renyah dan lembut yang memuaskan. Cocok disantap dengan nasi hangat dan teman-teman terdekat!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locUrl = "https://maps.app.goo.gl/PMNhsNu9WoJS594f6",
                rating = 5.0
            ),
            Menu(
                imgUrl = "https://raw.githubusercontent.com/akmalinnn/sasahursFood-assets/main/product_img/img_belut.webp",
                name = "Penyetan Belut",
                price = 17000.0,
                desc = "Berani mencoba sesuatu yang unik? Coba hidangan kami yang satu ini, Penyetan Belut! Rasakan kelezatan daging belut yang lembut dan nikmati variasi rasa sambal pedas khas kami yang membuat Anda ketagihan. Dipadukan dengan lalapan segar dan nasi hangat, hidangan ini akan memuaskan hasrat kuliner Anda yang penuh petualangan!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
                rating = 5.0
            ),
            Menu(
                imgUrl = "https://raw.githubusercontent.com/akmalinnn/sasahursFood-assets/main/product_img/img_tahu_tempe.webp",
                name = "Tahu Tempe",
                price = 12000.0,
                desc = "Menu makanan favorit dengan porsi yang besar, cocok untuk dimakan setiap hari. Rasakan kelezatan tahu dan tempe yang digoreng dengan sempurna. Disajikan dengan sambal pedas yang membangkitkan selera dan lalapan segar untuk menambah kesegaran. Setiap suapan akan membawa Anda merasakan cita rasa tradisional yang autentik!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
                rating = 5.0
            ),
            Menu(
                imgUrl = "https://raw.githubusercontent.com/akmalinnn/sasahursFood-assets/main/product_img/img_indomie.jpg",
                name = "Indomie Special",
                price = 8000.0,
                desc = "Indomie Special, indomie goreng disajikan dengan sempurna dengan tambahan telur goreng. Nikmati sensasi kelezatan mi goreng yang lezat dengan paduan telur yang gurih. Setiap gigitan akan membangkitkan kenangan masa kecil dan membuat Anda ingin kembali lagi dan lagi!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
                rating = 5.0
            ),
            Menu(
                imgUrl = "https://raw.githubusercontent.com/akmalinnn/sasahursFood-assets/main/product_img/img_esteh.webp",
                name = "Es Teh Manis",
                price = 3000.0,
                desc = "Tutuplah perjalanan kuliner Anda dengan manisnya Es Teh Manis kami. Minuman segar ini adalah pendamping sempurna untuk menyegarkan tenggorokan Anda setelah menikmati hidangan pedas kami. Disajikan dalam porsi sedang, cocok untuk menemani setiap makan",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
                rating = 5.0
            )
        )
    }
}
