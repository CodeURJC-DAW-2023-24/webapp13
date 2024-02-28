function enviarFormulario() {
    const form = document.getElementById('registroForm');
    const password = form.elements['password'].value;
    const confirmPassword = form.elements['repeatPassword'].value;

    if (password !== confirmPassword) {
        alert("Las contraseñas no coinciden");
        return false; // Evita que se envíe el formulario
    }else{
        return true
    }
} 
