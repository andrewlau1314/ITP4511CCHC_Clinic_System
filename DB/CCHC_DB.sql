DROP
DATABASE IF EXISTS CCHC_DB;
-- Create Database and Use it
CREATE
DATABASE CCHC_DB;
USE
CCHC_DB;

-- ==================== CCHC 診所系統 - 資料庫建表 SQL ====================

-- 1. 用戶表
CREATE TABLE users
(
    user_id    INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    full_name  VARCHAR(100),
    email      VARCHAR(100),
    phone      VARCHAR(20),
    role       ENUM('PATIENT','STAFF','ADMIN') NOT NULL,
    clinic_id  INT NULL,
    active     TINYINT  DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. 診所表
CREATE TABLE clinics
(
    clinic_id         INT PRIMARY KEY AUTO_INCREMENT,
    name              VARCHAR(100) NOT NULL,
    address           VARCHAR(255),
    phone             VARCHAR(20),
    day_off SET('Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'),
    lunch_break_start TIME         NOT NULL,
    lunch_break_end   TIME         NOT NULL,
    open_time         TIME         NOT NULL,
    close_time        TIME         NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. 服務表
CREATE TABLE services
(
    service_id   INT PRIMARY KEY AUTO_INCREMENT,
    service_name VARCHAR(100) NOT NULL,
    description  TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. 服務表 + 診所表
CREATE TABLE clinics_services
(
    clinic_id  INT,
    service_id INT,
    quota      INT DEFAULT 10,
    PRIMARY KEY (clinic_id, service_id),
    FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services (service_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. 預約表
CREATE TABLE appointments
(
    clinic_id        INT  NOT NULL,
    appointment_id   INT  NOT NULL AUTO_INCREMENT,
    user_id          INT  NOT NULL,
    service_id       INT  NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status           ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED', 'NO_SHOW') DEFAULT 'PENDING',
    cancel_reason    VARCHAR(255) DEFAULT NULL,
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    is_deleted       TINYINT(1) DEFAULT 0,

    PRIMARY KEY (clinic_id, appointment_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id),
    FOREIGN KEY (service_id) REFERENCES services (service_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;


-- 4. 時段表（Timeslots）
CREATE TABLE timeslots
(
    timeslot_id INT PRIMARY KEY AUTO_INCREMENT,
    clinic_id   INT,
    service_id  INT,
    date        DATE NOT NULL,
    start_time  TIME NOT NULL,
    end_time    TIME NOT NULL,
    quota       INT  NOT NULL DEFAULT 10,
    booked      INT           DEFAULT 0,
    FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id),
    FOREIGN KEY (service_id) REFERENCES services (service_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. 排隊表
CREATE TABLE queues (
    queue_id           INT PRIMARY KEY AUTO_INCREMENT,
    user_id            INT,
    clinic_id          INT,
    service_id         INT,
    date               DATE NOT NULL,
    queue_number       VARCHAR(10),
    status             ENUM('WAITING','CALLED','SKIPPED','COMPLETED','EXPIRED') DEFAULT 'WAITING',
    estimated_wait_min INT,
    joined_at          DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id),
    FOREIGN KEY (service_id) REFERENCES services (service_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 7. 通知表
CREATE TABLE notifications (
    notif_id    INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT NOT NULL,
    title       VARCHAR(100) NOT NULL,
    message     TEXT,
    is_read     TINYINT DEFAULT 0,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    type        ENUM('APPOINTMENT', 'QUEUE', 'SYSTEM') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 8. 操作記錄表（Extra Feature 用）
-- CREATE TABLE audit_log
-- (
--     log_id     INT PRIMARY KEY AUTO_INCREMENT,
--     user_id    INT,
--     action     VARCHAR(100),
--     details    TEXT,
--     ip_address VARCHAR(45),
--     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY (user_id) REFERENCES users (user_id)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================== 完成 ====================
-- ==================== Test Case ====================
INSERT INTO users (username, password, full_name, email, phone, role, clinic_id, active)
VALUES ('admin', '123456', 'FAN Da Men', 'admin@cchc.hk', '12345678', 'ADMIN', NULL, 1),

       ('staff1', '123456', 'Li Da Men', 'staff1@cchc.hk', '98765432', 'STAFF', 1, 1),   -- Chai Wan
       ('staff2', '123456', 'Chan Da Men', 'staff2@cchc.hk', '91234567', 'STAFF', 2, 1), -- Tseung Kwan O

       ('patient1', '123456', 'Zhang Da Men', 'patient1@gmail.com', '55556666', 'PATIENT', NULL, 1),
       ('patient2', '123456', 'Wa Da Men', 'patient2@gmail.com', '66667777', 'PATIENT', NULL, 1);

-- 1. 診所資料
INSERT INTO clinics (name, address, phone, day_off, open_time, lunch_break_start, lunch_break_end, close_time)
VALUES ('Chai Wan Clinic', 'Shop 402, 4/F, New Jade Shopping Arcade, 233 Chai Wan Road, Chai Wan', '2555 1234', 'Mon',
        '09:00:00', '13:00:00', '14:00:00', '18:00:00'),
       ('Tseung Kwan O Clinic', 'G/F, Shop G20, Metro City Phase II, 8 Yan King Road, Tseung Kwan O', '2666 5678',
        'Sat,Sun', '10:00:00', '13:30:00', '14:30:00', '19:00:00'),
       ('Sha Tin Clinic', 'Level 1, Shop 105, New Town Plaza Phase III, 18 Sha Tin Centre Street, Sha Tin', '2777 9012',
        'Wed', '09:30:00', '13:00:00', '14:00:00', '18:30:00'),
       ('Tuen Mun Clinic', 'Shop 312, 3/F, K-Point, 1 Tuen Lung Street, Tuen Mun', '2888 3456', 'Sat,Sun', '08:30:00',
        '12:30:00', '13:30:00', '17:30:00'),
       ('Tsing Yi Clinic', 'Shop 45, U2 Level, Tsing Yi Station, Tsing Yi', '2999 7890', 'Sun', '09:00:00', '13:00:00',
        '14:00:00', '21:00:00');

-- 2. 服務資料
INSERT INTO services (service_name, description)
VALUES ('General Consultation', 'Routine medical check-up and consultation for common illnesses.'),
       ('Vaccination Service', 'Includes Flu, HPV, and other seasonal vaccine injections.'),
       ('Physical Examination', 'Comprehensive health screening and body check reports.'),
       ('Pediatric Care', 'Specialized medical services for infants and children.'),
       ('Dermatology Consultation', 'Diagnosis and treatment of skin, hair, and nail conditions.'),
       ('Dental Scaling', 'Professional teeth cleaning and oral health assessment.'),
       ('Traditional Chinese Medicine', 'Acupuncture and herbal medicine consultation sessions.'),
       ('Physiotherapy', 'Rehabilitation and manual therapy for muscle and joint pain.'),
       ('Mental Health Counseling', 'Professional psychological support and private counseling.');


INSERT INTO clinics_services (clinic_id, service_id, quota)
VALUES
-- 柴灣診所 (Clinic 1)
(1, 1, 30), -- 普通科 (30名額)
(1, 2, 10), -- 疫苗服務
(1, 4, 5),  -- 兒科
(1, 5, 5),
(1, 6, 10),


-- 將軍澳診所 (Clinic 2)
(2, 1, 25),
(2, 2, 20),
(2, 4, 10),
(2, 6, 10), -- 牙科洗牙

-- 沙田診所 (Clinic 3)
(3, 1, 40),
(3, 3, 15), -- 體檢
(3, 7, 20), -- 中醫
(3, 9, 10),
-- 屯門診所 (Clinic 4)
(4, 1, 30),
(4, 2, 15),
(4, 6, 15),
(4, 8, 10), -- 物理治療

-- 青衣診所 (Clinic 5)
(5, 1, 20),
(5, 5, 10), -- 皮膚科
(5, 9, 5);
-- 心理輔導


-- -- 3. 時段資料（今天起未來 7 天，每個時段 quota 10 人）
-- INSERT INTO timeslots (clinic_id, service_id, date, start_time, end_time, quota, booked) VALUES
-- (1, 1, CURDATE(), '09:00:00', '09:15:00', 10, 3),
-- (1, 1, CURDATE(), '09:15:00', '09:30:00', 10, 1),
-- (1, 1, CURDATE(), '10:00:00', '10:15:00', 10, 0),
-- (1, 2, CURDATE(), '14:00:00', '14:10:00', 8, 2),
-- (2, 3, CURDATE() + INTERVAL 1 DAY, '09:00:00', '09:15:00', 10, 0),
-- (3, 5, CURDATE(), '11:00:00', '11:15:00', 10, 4);


INSERT INTO appointments (user_id, clinic_id, service_id, appointment_date, appointment_time, status, cancel_reason)
VALUES
-- 1. PENDING (待處理): 設定為未來的日期
(1, 1, 1, '2026-05-01', '09:00:00', 'PENDING', NULL),

-- 2. CONFIRMED (已確認): 患者已預約並由系統/職員確認
(1, 1, 1, '2026-05-02', '10:00:00', 'CONFIRMED', NULL),

-- 3. CANCELLED (已取消): 包含取消原因
(1, 2, 1, '2026-04-20', '11:30:00', 'CANCELLED', 'Patient had a last-minute emergency'),

-- 4. COMPLETED (已完成): 代表病人已就診完畢
(1, 2, 1, '2026-04-21', '14:00:00', 'COMPLETED', NULL),

-- 5. NO_SHOW (缺席): 代表病人未按時報到
<<<<<<< HEAD
(1, 1, 1, '2026-04-22', '16:30:00', 'NO_SHOW', NULL),
(1, 1, 1, '2026-05-05', '09:30:00', 'CONFIRMED', NULL),
(2, 1, 2, '2026-05-05', '10:00:00', 'PENDING', NULL),
(3, 1, 1, '2026-05-05', '10:30:00', 'CONFIRMED', NULL),
(4, 2, 3, '2026-05-05', '11:00:00', 'CANCELLED', 'Doctor on leave'),
(5, 2, 1, '2026-05-05', '13:00:00', 'CONFIRMED', NULL),
(1, 1, 2, '2026-05-05', '14:30:00', 'PENDING', NULL),
(2, 2, 1, '2026-05-05', '15:00:00', 'COMPLETED', NULL),
(3, 1, 3, '2026-05-05', '16:00:00', 'NO_SHOW', NULL),
(4, 1, 1, '2026-05-05', '16:30:00', 'CONFIRMED', NULL),
(5, 2, 2, '2026-05-05', '17:00:00', 'PENDING', NULL);
=======
(1, 1, 1, '2026-04-22', '16:30:00', 'NO_SHOW', NULL);


INSERT INTO timeslots (clinic_id, service_id, date, start_time, end_time, quota, booked) VALUES
(1, 1, '2026-04-28', '09:00:00', '09:15:00', 10, 2),
(1, 1, '2026-04-28', '09:15:00', '09:30:00', 10, 0),
(1, 1, '2026-04-28', '10:00:00', '10:15:00', 10, 5),
(1, 1, '2026-04-29', '09:00:00', '09:15:00', 10, 0),
(1, 2, '2026-04-28', '14:00:00', '14:10:00', 8, 3);
>>>>>>> 23f47f4951b1b572c515471246f6b6db5e85d91f
