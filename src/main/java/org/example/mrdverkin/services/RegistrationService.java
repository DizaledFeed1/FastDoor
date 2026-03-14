package org.example.mrdverkin.services;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    //todo при регистрации проверяем, если роль INSTALLER и в бд установщиков есть запись с указанным номер то склеиваем user и installer записи в БД,
    // если указан Installer а в бд установщиков такой записи нет то ошибка, если указан не INSTALLER то обычная регистрация.
    // В ДТО решистрации добавить необезательное поле номер на фронте появляется только тогда когда выбрали INSTALLER
}
