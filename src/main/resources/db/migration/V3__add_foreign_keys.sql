ALTER TABLE trip
    ADD CONSTRAINT fk_trip_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id);


ALTER TABLE trip
    ADD CONSTRAINT fk_trip_vehicle_id
        FOREIGN KEY (vehicle_id)
            REFERENCES vehicle (id);