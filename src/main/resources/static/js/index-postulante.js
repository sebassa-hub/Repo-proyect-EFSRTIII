$(document).ready(function () {
    const tabla = $('#tablaPostulantes').DataTable({
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

    const input = $('#buscarInput');
    const filtro = $('#filtroTipo');

    $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
        const tipo = filtro.val();
        const valor = input.val().toLowerCase();

        const apellidos = data[2].toLowerCase();        // Columna 0: ID
        const dni = data[3].toLowerCase();  // Columna 1: Nombre

		if (tipo === "apellido" && apellidos.includes(valor)) return true;
		if (tipo === "documento" && dni.includes(valor)) return true;
        
        return valor === "";
    });

    input.on("input", function () {
        tabla.draw();
    });

    filtro.on("change", function () {
        tabla.draw();
    });
});
