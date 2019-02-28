if (typeof links === 'undefined') {
    links = {};
    links.locales = {};
} else if (typeof links.locales === 'undefined') {
    links.locales = {};
}

links.locales['es'] = {
    'MONTHS': ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
    'MONTHS_SHORT': ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
    'DAYS': ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"],
    'DAYS_SHORT': ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
    'ZOOM_IN': "Acercar",
    'ZOOM_OUT': "Alejar",
    'MOVE_LEFT': "Mover a la izquierda",
    'MOVE_RIGHT': "Mover a la derecha",
    'NEW': "Nuevo",
    'CREATE_NEW_EVENT': "Crear evento"
};