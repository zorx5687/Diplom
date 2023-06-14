ALTER TABLE post
        ALTER COLUMN description TYPE TEXT USING (description::TEXT);

INSERT INTO users (id, name, surname, email, phone, created_time)
VALUES (0, 'паша', 'ыва', 'sad@maw.ru', '+7-(912)-123-12-32', NULL);

INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Шахматы Дуб ручная работа', 'Санкт-Петербург, пос. Парголово, Заречная ул., 15к1',
        'NEW! Шахматы дубовые ручной работы! Подходит начинающим и гроссмейстерам! Роскошный шахматный набор как для подарка самому себе, так и человеку, у которого уже все есть! Впишется в любой интерьер и будет радовать вас часами, проведенными в великолепной компании! Размер 40см х 40см Размер игрового поля 32 х 32 см  ',
        12000, 'WOOD', 'https://i.ibb.co/ZLQLRH6/1-i-BSWv-La4-JP2g-Feb4ipq-QN6we-Jvso-Hab14-Bgm-y-YVLPcg.webp', NOW());
INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Картина из бисера',
        'Ростовская область, Ростов-на-Дону, посёлок Орджоникидзе, пр-т 20-летия Октября р-н Первомайский',
        'Картина вышивка бисером , ручная работа, высота 51, ширина 41, на свету бисер сияет',
        6000, 'BEADS', 'https://i.ibb.co/Sxx5njB/1-9-M3h-NLa4-WCTXn-Zohh-Ue9t9m-WWi-Jfldosl5-Ba-Jl-Gd-UC5-X.webp',
        NOW());
INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Чайник из исинской глины Тыква',
        'Ростовская область, Ростов-на-Дону, микрорайон Северный, б-р Комарова, 18/3с1 р-н Ворошиловский',
        'Продам шикарный чайник из исина на 90 мл в виде Тыквы. Ручная работа, паспорт прилагаю.',
        9000, 'CLAY', 'https://i.ibb.co/Q6P3FzJ/1-k-MYQ97a4-PC8m-Xv4q-PPny9yl-VPimu-Vr4n-Zl-M-La-Be-NCWm.webp', NOW());
INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Шахматы Дуб ручная работа', 'Санкт-Петербург, пос. Парголово, Заречная ул., 15к1',
        'NEW! Шахматы дубовые ручной работы! Подходит начинающим и гроссмейстерам! Роскошный шахматный набор как для подарка самому себе, так и человеку, у которого уже все есть! Впишется в любой интерьер и будет радовать вас часами, проведенными в великолепной компании! Размер 40см х 40см Размер игрового поля 32 х 32 см  ',
        12000, 'EMBROIDERY', 'https://i.ibb.co/ZLQLRH6/1-i-BSWv-La4-JP2g-Feb4ipq-QN6we-Jvso-Hab14-Bgm-y-YVLPcg.webp',
        NOW());
INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Монета-жетон "Автомобиль Кузьмина" ммд.2016г',
        'Ростовская область, Ростов-на-Дону, ул. Ерёменко
р-н Советский',
        'Продам из новой серии ММД из "Уроки истории"-Символическую монету-жетон "Автомобиль Кузьмина" ( по классификации нумизматов жетон ). Тираж всего 5000 шт. Диаметр уже больше 28 мм.,гурт рифленый,метал Нейзильбер., клеймо ММД.Состояние UNC! Реплика сертификата прилагается.',
        450, 'IRON', 'https://i.ibb.co/XzK6xCy/1-VSU9xba5-cw-Lb-Dv-JG9-R8-TJpm-8a-J5v-EOj-Gb7y-IFs884.webp', NOW());
INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Сумка летняя', 'Республика Татарстан, Набережные Челны, пр-т Мира
р-н Центральный',
        'NEW! Шахматы дубовые ручной работы! Подходит начинающим и гроссмейстерам! Роскошный шахматный набор как для подарка самому себе, так и человеку, у которого уже все есть! Впишется в любой интерьер и будет радовать вас часами, проведенными в великолепной компании! Размер 40см х 40см Размер игрового поля 32 х 32 см  ',
        1500, 'KNITTING', 'https://i.ibb.co/Sv17czn/1-Oy1-ITLa5l8-R-5-VXBMk-Rjaq3vkc78b58-G-e-Vw-PTlnc-Y.webp',
        NOW());
INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Брошь "Мускари". Ручная лепка', 'Ярославская область, Ярославль, ул. Свободы, 63
р-н Кировский
',
        '🍃🍃🍃Брошь «Мускари». Ручная работа представлена в одном экземпляре! Прекрасный подарок для себя и близких! Инструкция по применению и гарантия прилагаются.

🍃Цветы выполнены из качественной бельгийской запекаемой полимерной глины. Смотрите больше вариантов в моем профиле.🤗',
        2500, 'MODELING', 'https://i.ibb.co/gjdVjCG/1-MBRSrba4n-P1k-BF74-Po-UIM2o-Pnvvs-DB71-JAme-IEl-Pfk.webp',
        NOW());
INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Изделия из гипса ручная работа', 'Пермский край, Пермь, ул. Макаренко
р-н Мотовилихинский',
        'NEW! Шахматы дубовые ручной работы! Подходит начинающим и гроссмейстерам! Роскошный шахматный набор как для подарка самому себе, так и человеку, у которого уже все есть! Впишется в любой интерьер и будет радовать вас часами, проведенными в великолепной компании! Размер 40см х 40см Размер игрового поля 32 х 32 см  ',
        12000, 'OTHER', 'https://i.ibb.co/qjbT1kZ/1-i-N5-TXLa4-JDdl9e-Yy-T1a-Jh-Wv-Jj-Ht-a-Y-Jfgm-Ne-P1-LD3l.webp',
        NOW());
INSERT INTO post (author_id, title, address, description, price, category, url, created_time)
VALUES (0, 'Шахматы Дуб ручная работа', 'Санкт-Петербург, пос. Парголово, Заречная ул., 15к1',
        'Изготовление изделий из гипса. Ручная работа.
Расцветки разные.

Изделия проходят обработку: шкурятся, обрабатываются грунтовкой глубокого проникновения и лаком.',
        100, 'WOOD', 'https://i.ibb.co/ZLQLRH6/1-i-BSWv-La4-JP2g-Feb4ipq-QN6we-Jvso-Hab14-Bgm-y-YVLPcg.webp', NOW());