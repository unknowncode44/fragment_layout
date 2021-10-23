# fragment_layout
FragmentLayout

FragmentLayout es un proyecto en kotlin para la materia Desarrollo de Software 1 de la carrera Tecnico Superior en Desarrollo de Software del Instituto Tecnico Superior en hardware.

El MainActivity es un contenedor que contendra dos fragments en su interior. Uno de ellos tendra dentro un RecyclerView y el otro un ViewPager, todo para mostrar informacion del contacto, es decir se seleccionara uno de lista y mostrara la informacion en el view pager. Se utiliza una vista Landscape, osea horizontal para mostrar los dos fragment en una sola pantalla.


Fragment 1
view id: recycler_contacts

Fragment2
view id: contentPanel

Se utilizara un cardview para mostrar el contacto en el list view 

layout: contact_card.xml

id nombre: contact_name
id numero: contact_number
id email: contact_email
id imagen: contact_image

