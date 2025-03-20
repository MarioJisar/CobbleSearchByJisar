document.addEventListener("DOMContentLoaded", async function () {
    let data = [];
    let filteredData = [];
    let currentPage = 1;
    const itemsPerPage = 50;

    const tbody = document.getElementById("results");
    const searchInput = document.getElementById("search");
    const parametroSelect = document.getElementById("parametro");
    const prevBtn = document.getElementById("prevPage");
    const nextBtn = document.getElementById("nextPage");
    const pokemonPopup = document.getElementById("pokemon-popup");
    const pokemonImg = document.getElementById("pokemon-img");

    async function cargarDatos() {
        try {
            const response = await fetch('data2.json');
            if (!response.ok) throw new Error(`Error ${response.status}: No se pudo cargar el JSON`);
            data = await response.json();
            filteredData = [...data]; // No filtramos nada al inicio
            actualizarTabla();
            actualizarVisibilidadColumnas(); // Oculta columnas no marcadas
        } catch (error) {
            console.error("Error cargando el JSON:", error);
        }
    }

    searchInput.addEventListener("input", function () {
        currentPage = 1;
        buscarPokemon();
    });

    function buscarPokemon() {
        const query = searchInput.value.trim().toLowerCase();
        const parametro = parametroSelect.value;
        filteredData = data.filter(pokemon =>
            pokemon[parametro] && pokemon[parametro].toString().toLowerCase().startsWith(query)
        );
        currentPage = 1;
        actualizarTabla();
    }

    function actualizarTabla() {
        tbody.innerHTML = "";
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const currentItems = filteredData.slice(start, end);

        currentItems.forEach(pokemon => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td class="col-No">${pokemon.No}</td>
                <td class="col-Pokemon">
                    <span class="pokemon" data-pokemon="${pokemon.No}">${pokemon.Pokemon}</span>
                </td>
                <td class="col-Entry">${pokemon.Entry}</td>
                <td class="col-Bucket">${pokemon.Bucket}</td>
                <td class="col-Weight">${pokemon.Weight}</td>
                <td class="col-LvMin">${pokemon.LvMin}</td>
                <td class="col-LvMax">${pokemon.LvMax}</td>
                <td class="col-Biomes">${pokemon.Biomes}</td>
                <td class="col-ExcludedBiomes">${pokemon.ExcludedBiomes}</td>
                <td class="col-Time">${pokemon.Time}</td>
                <td class="col-Weather">${pokemon.Weather}</td>
                <td class="col-Multipliers">${pokemon.Multipliers}</td>
                <td class="col-Context">${pokemon.Context}</td>
                <td class="col-Presets">${pokemon.Presets}</td>
                <td class="col-Conditions">${pokemon.Conditions}</td>
                <td class="col-Anticonditions">${pokemon.Anticonditions}</td>
                <td class="col-skyLightMin">${pokemon.skyLightMin}</td>
                <td class="col-skyLightMax">${pokemon.skyLightMax}</td>
                <td class="col-canSeeSky">${pokemon.canSeeSky}</td>
                <td class="col-Implemented ${getColorClass(pokemon.Implemented)}">${pokemon.Implemented}</td>
            `;

            tbody.appendChild(row);
        });

        actualizarVisibilidadColumnas();
        actualizarPaginacion();
        asignarEventosImagen();
    }

    function actualizarVisibilidadColumnas() {
        const columnas = [
            'No', 'Pokemon', 'Entry', 'Bucket', 'Weight', 'LvMin', 'LvMax', 'Biomes', 'ExcludedBiomes', 'Time', 
            'Weather', 'Multipliers', 'Context', 'Presets', 'Conditions', 'Anticonditions', 'skyLightMin', 
            'skyLightMax', 'canSeeSky', 'Implemented'
        ];

        columnas.forEach(columna => {
            const checkbox = document.getElementById(`toggle-${columna}`);
            const th = document.getElementById(`col-${columna}`);
            const tds = document.querySelectorAll(`.col-${columna}`);

            if (checkbox && !checkbox.checked) {
                if (th) th.style.display = 'none';
                tds.forEach(td => td.style.display = 'none');
            } else {
                if (th) th.style.display = '';
                tds.forEach(td => td.style.display = '');
            }
        });
    }

    document.querySelectorAll('.toggle-column').forEach(checkbox => {
        checkbox.addEventListener("change", actualizarVisibilidadColumnas);
    });

    prevBtn.addEventListener("click", function () {
        if (currentPage > 1) {
            currentPage--;
            actualizarTabla();
        }
    });

    nextBtn.addEventListener("click", function () {
        const totalPages = Math.ceil(filteredData.length / itemsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            actualizarTabla();
        }
    });

    function actualizarPaginacion() {
        prevBtn.disabled = currentPage === 1;
        nextBtn.disabled = currentPage >= Math.ceil(filteredData.length / itemsPerPage);
    }

    function asignarEventosImagen() {
        document.querySelectorAll('.pokemon').forEach(element => {
            element.addEventListener('mouseenter', async (e) => {
                const pokemonId = e.target.dataset.pokemon.replace(/^0+/, ''); // Quita ceros iniciales
                pokemonImg.src = `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png`;
                pokemonPopup.style.display = 'block';
                pokemonPopup.style.top = `${e.pageY + 10}px`;
                pokemonPopup.style.left = `${e.pageX + 10}px`;
            });
            element.addEventListener('mouseleave', () => {
                pokemonPopup.style.display = 'none';
            });
        });
    }

    function getColorClass(value) {
        if (!value) return "";
        switch (value.toLowerCase()) {
            case "yes": return "green-bg";
            case "no": return "red-bg";
            case "coming soon": return "orange-bg";
            default: return "";
        }
    }

    cargarDatos();
});
