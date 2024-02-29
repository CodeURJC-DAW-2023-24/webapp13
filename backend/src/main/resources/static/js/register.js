function enviarFormulario() {
    const form = document.getElementById('registroForm');
    const name = form.elements['name'].value;
    const username = form.elements['username'].value;
    const email = form.elements['email'].value; // Corregido el nombre del campo
    const password = form.elements['password'].value;
    const confirmPassword = form.elements['repeatPassword'].value;

    if (password !== confirmPassword) {
        alert("Las contraseñas no coinciden");
        return false;
    } else if (name === "" || username === "" || email === "" || password === "") {
        alert("Por favor, completa todos los campos.");
        return false;
    }
    return true
}
