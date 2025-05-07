const handleLogin = async (event: React.FormEvent) => {
  event.preventDefault();

  const email = (
    document.querySelector('input[name="email"]') as HTMLInputElement
  ).value;
  const password = (
    document.querySelector('input[name="password"]') as HTMLInputElement
  ).value;

  try {
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    });

    if (response.ok) {
      const data = await response.json();
      localStorage.setItem("token", data.token);
      alert("!Login Succesfully!");
    } else if (response.status === 403) {
      alert("Credenciales inválidas");
    } else {
      throw new Error("Ocurrio un error en la conexión");
    }
  } catch (error) {
    console.error("Error: " + error);
    alert("Ocurrió un error al intentar iniciar sesión.");
  }
};

export default handleLogin;
