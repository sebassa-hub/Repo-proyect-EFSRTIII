// Espera a que el DOM esté completamente cargado antes de ejecutar cualquier lógica
$(document).ready(function () {

    // INICIALIZA EL PLUGIN DATATABLES SOBRE LA TABLA DE PACIENTES
    const tabla = $('#tablaPacientes').DataTable({
        pageLength: 6,              // Muestra 6 registros por página
        lengthChange: false,        // Oculta el selector de cantidad de registros por página
		language: {
				    lengthMenu: "Mostrar _MENU_ registros por página",
				    zeroRecords: "No se encontraron resultados",
				    info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
				    infoEmpty: "Mostrando 0 a 0 de 0 registros",
				    infoFiltered: "(filtrado de _MAX_ registros totales)",
				    search: "Buscar:",
				    paginate: {
				        first: "Primero",
				        last: "Último",
				        next: "Siguiente",
				        previous: "Anterior"
						}
		        },

    });

    // OBTIENE LAS REFERENCIAS AL INPUT DE BÚSQUEDA PERSONALIZADO Y AL SELECT DEL TIPO DE FILTRO
    const input = $('#buscarInput');
    const filtro = $('#filtroTipo');

    // PAGINACIÓN DE LA TABLA Y BÚSQUEDA PERSONALIZADA
    $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
        const tipo = filtro.val();                  // "dni" o "apellido"
        const valor = input.val().toLowerCase();

        const dni = data[3].toLowerCase();          // Columna DNI
        const apellido = data[2].toLowerCase();     // Columna Apellido

        if (tipo === "documento" && dni.includes(valor)) return true;
        if (tipo === "apellido" && apellido.includes(valor)) return true;

        return valor === "";
    });

    input.on("input", function () {
        tabla.draw();
    });

    filtro.on("change", function () {
        tabla.draw();
    });

    // ✅ CONFIRMACIÓN DE ELIMINACIÓN CON SWEETALERT2
    $('.btn-eliminar').click(function (e) {
        e.preventDefault();

        const id = $(this).data('id');
        const nombre = $(this).data('nombre');
        const apellido = $(this).data('apellido');
        const documento = $(this).data('documento');
        const eliminarUrl = `/paciente/eliminar/${id}`;

        Swal.fire({
            title: '¿Estás seguro?',
            html: `¿Deseas eliminar al paciente <strong>${nombre} ${apellido}</strong> con N° Doc. <strong>${documento}</strong>?`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = eliminarUrl;
            }
        });
    });

});
