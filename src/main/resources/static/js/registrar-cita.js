$(document).ready(function () {

    // ✅ Seleccionar hora libre
    window.seleccionarHora = function (hora, esOcupado) {
        if (esOcupado) {
            alert("⚠️ Esta hora ya está ocupada.");
            return;
        }
        $('#horaCita').val(hora);
        $('#bloques button').removeClass('btn-primary active');
        $(`#bloques button[data-hora='${hora}']`).addClass('btn-primary active');
    }

    // ✅ Consultar horarios disponibles
    $('#btnConsultar').click(function () {
        const medico = $('#idMedico').val();
        const fecha = $('#fechaCita').val();

        if (!medico || !fecha) {
            alert('Seleccione médico y fecha');
            return;
        }

        $.ajax({
            url: '/cita/horarios-disponibles',
            method: 'GET',
            data: { idMedico: medico, fechaCita: fecha },
            success: function (bloques) {
                let bloquesHtml = '';
                if (bloques.length === 0) {
                    bloquesHtml = '<div class="text-danger">⛔ El médico no tiene horario disponible para este día.</div>';
                } else {
                    bloques.forEach(bloque => {
                        if (bloque.ocupado) {
                            bloquesHtml += `<button type="button" class="btn btn-danger m-1" data-hora="${bloque.hora}" disabled>${bloque.hora}</button>`;
                        } else {
                            bloquesHtml += `<button type="button" class="btn btn-success m-1" data-hora="${bloque.hora}" onclick="seleccionarHora('${bloque.hora}', false)">${bloque.hora}</button>`;
                        }
                    });
                }
                $('#bloques').html(bloquesHtml);
            },
            error: function () {
                $('#bloques').html('<div class="text-danger">⚠️ Error al consultar horarios.</div>');
            }
        });
    });

    // ✅ Cargar médicos al seleccionar especialidad
    $('#idEspecialidad').change(function () {
        const idEspecialidad = $(this).val();
        $.ajax({
            url: '/cita/medicos-por-especialidad',
            method: 'GET',
            data: { idEspecialidad },
            success: function (medicos) {
                let opciones = '<option value="" disabled selected>Seleccione médico</option>';
                medicos.forEach(m => {
                    opciones += `<option value="${m.idMedico}">${m.nombres} ${m.apellidos}</option>`;
                });
                $('#idMedico').html(opciones);
            }
        });
    });

    // ✅ FILTROS POR AJAX (Sin recargar página)
    $(document).on('submit', '#formFiltros', function(e) {
        e.preventDefault();
        const url = $(this).attr('action');
        const datos = $(this).serialize();

        $.ajax({
            url: url,
            type: 'GET',
            data: datos,
            headers: { "X-Requested-With": "XMLHttpRequest" }, // Importante para el Controller
            success: function(resultado) {
                // Solo reemplazamos el contenido del contenedor de la tabla
                $('#contenedorTabla').html($(resultado).find('#contenedorTabla').html());
            }
        });
    });

    // ✅ CAMBIAR ESTADO POR AJAX (Para evitar que la página salte arriba)
    $(document).on('submit', '.form-cambiar-estado', function(e) {
        e.preventDefault();
        const form = $(this);
        const url = form.attr('action');
        const datos = form.serialize();

        $.ajax({
            url: url,
            type: 'POST',
            data: datos,
            success: function() {
                // Recargamos la tabla para ver el cambio reflejado (usando el filtro actual si lo hay)
                $('#formFiltros').submit();
            }
        });
    });

    // ✅ BOTÓN LIMPIAR POR AJAX
    $(document).on('click', '#btnLimpiar', function(e) {
        e.preventDefault();
        $('#formFiltros')[0].reset(); // Limpia los selects
        $('#formFiltros').submit();   // Envía el formulario vacío para traer todo
    });

});