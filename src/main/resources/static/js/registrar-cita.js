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
                            bloquesHtml += `<button type="button" class="btn btn-danger" data-hora="${bloque.hora}" onclick="seleccionarHora('${bloque.hora}', true)" disabled>${bloque.hora}</button>`;
                        } else {
                            bloquesHtml += `<button type="button" class="btn btn-success" data-hora="${bloque.hora}" onclick="seleccionarHora('${bloque.hora}', false)">${bloque.hora}</button>`;
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
                let opciones = '<option value="" disabled selected>Seleccione</option>';
                medicos.forEach(m => {
                    opciones += `<option value="${m.idMedico}">${m.nombres} ${m.apellidos}</option>`;
                });
                $('#idMedico').html(opciones);
            }
        });
    });

});
