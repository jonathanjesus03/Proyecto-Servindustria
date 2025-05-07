const handleRegister = async (event: React.FormEvent) => {
  event.preventDefault();

  const name = (
    document.querySelector('input[name="name"]') as HTMLInputElement
  ).value;
  const lastname = (
    document.querySelector('input[name="last_name"]') as HTMLInputElement
  ).value;
  const telephone = (
    document.querySelector('input[name="telephone"]') as HTMLInputElement
  ).value;
  const birthDay = (
    document.querySelector('input[name="date_bithday"]') as HTMLInputElement
  ).value;
  const email = (
    document.querySelector('input[name="email_register"]') as HTMLInputElement
  ).value;
  const doc = (document.querySelector('input[name="doc"]') as HTMLInputElement)
    .value;
  const password = (
    document.querySelector(
      'input[name="password_register"]'
    ) as HTMLInputElement
  ).value;
  const passwrod_confirm = (
    document.querySelector('input[name="password_confirm"]') as HTMLInputElement
  ).value;
  console.log(`|${password}| --- |${passwrod_confirm}|`);

  try {
    if (password !== passwrod_confirm) {
      throw new Error("Las contraseñas no coinciden");
    }

    const response = await fetch("http://localhost:8080/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        lastname,
        telephone,
        birthDay,
        email,
        doc,
        password,
      }),
    });

    if (response.ok) {
      const data = await response.json();
      localStorage.setItem("token", data.token);
      alert("!Register Succesfully!");
    } else {
      throw new Error("Ocurrio un error en la conexión");
    }
  } catch (error) {
    console.error("Error: " + error);
    alert(error);
  }
};

export default handleRegister;
