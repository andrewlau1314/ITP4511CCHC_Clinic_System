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
    clinic_id INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    address   VARCHAR(255),
    phone     VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. 服務表
CREATE TABLE services
(
    service_id       INT PRIMARY KEY AUTO_INCREMENT,
    clinic_id        INT,
    service_name     VARCHAR(100) NOT NULL,
    description      TEXT,
    duration_minutes INT     DEFAULT 15,
    walk_in_enabled  TINYINT DEFAULT 1,
    FOREIGN KEY (clinic_id) REFERENCES clinics (clinic_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. 時段表
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

-- 5. 預約表
CREATE TABLE appointments
(
    appointment_id   INT PRIMARY KEY AUTO_INCREMENT,
    user_id          INT,
    timeslot_id      INT,
    status           ENUM('PENDING','CONFIRMED','CANCELLED','COMPLETED','NO_SHOW') DEFAULT 'PENDING',
    created_at       DATETIME DEFAULT CURRENT_TIMESTAMP,
    cancelled_reason TEXT,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (timeslot_id) REFERENCES timeslots (timeslot_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. 排隊表
CREATE TABLE queues
(
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

-- 7. 通知表
CREATE TABLE notifications
(
    notif_id   INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT,
    title      VARCHAR(100),
    message    TEXT,
    is_read    TINYINT  DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    type       ENUM('APPOINTMENT','QUEUE','SYSTEM'),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. 操作記錄表（Extra Feature 用）
CREATE TABLE audit_log
(
    log_id     INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT,
    action     VARCHAR(100),
    details    TEXT,
    ip_address VARCHAR(45),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================== 完成 ====================
-- ==================== Test Case ====================
INSERT INTO users (username, password, full_name, email, phone, role, clinic_id, active) VALUES
('admin', '123456', '系統管理員', 'admin@cchc.hk', '12345678', 'ADMIN', NULL, 1),

('staff1', '123456', '陳護理', 'staff1@cchc.hk', '98765432', 'STAFF', 1, 1),   -- Chai Wan
('staff2', '123456', '李護士', 'staff2@cchc.hk', '91234567', 'STAFF', 2, 1),   -- Tseung Kwan O

('patient1', '123456', '張小明', 'patient1@gmail.com', '55556666', 'PATIENT', NULL, 1),
('patient2', '123456', '李小華', 'patient2@gmail.com', '66667777', 'PATIENT', NULL, 1);