$(document).ready(function () {

    // INICIALIZA EL PLUGIN DATATABLES SOBRE LA TABLA DE MÉDICOS
    const tabla = $('#tablaMedicos').DataTable({
        pageLength: 6,
        lengthChange: false,
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

    // ELEMENTOS DE BÚSQUEDA PERSONALIZADA
    const input = $('#buscarInput');
    const filtro = $('#filtroTipo');

    // BÚSQUEDA PERSONALIZADA POR TIPO
    $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
        const tipo = filtro.val();               // 'documento' o 'apellido'
        const valor = input.val().trim().toLowerCase();

        const documento = data[2]?.toLowerCase();           // Columna N° Documento
        const nombreCompleto = data[1]?.toLowerCase();       // Columna Nombre Completo

        if (tipo === "documento" && documento.includes(valor)) return true;
        if (tipo === "apellido" && nombreCompleto.includes(valor)) return true;

        return valor === "";
    });

    // EVENTOS DE INPUT Y CAMBIO PARA REDIBUJAR LA TABLA
    input.on("input", () => tabla.draw());
    filtro.on("change", () => tabla.draw());

    // ✅ SWEETALERT2 PARA CONFIRMACIÓN DE ELIMINACIÓN
    $('.btn-eliminar').click(function (e) {
        e.preventDefault();

        const id = $(this).data('id');
        const nombre = $(this).data('nombre');
        const apellido = $(this).data('apellido');
        const documento = $(this).data('documento');
        const eliminarUrl = `/medico/eliminar/${id}`;

        Swal.fire({
            title: '¿Estás seguro?',
            html: `¿Deseas eliminar al médico <strong>${nombre} ${apellido}</strong> con N° Doc. <strong>${documento}</strong>?`,
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
