import { useState } from "react";
import { useNavigate } from "react-router-dom";

function LoginForm() {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showMessage, setShowMessage] = useState(false);

  function handleNameChange(e) {
    setUsername(e.target.value);
  }

  function handlePasswordChange(e) {
    setPassword(e.target.value);
  }

  async function handleLogin() {
        const response = await fetch('api/user/signin', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
    });
    if (!response.ok) {
      setShowMessage(true);
      throw new Error("No trainer with this username or password" + JSON.stringify(response.body));
    } else {
      const jwt = (await response.json())
      localStorage.setItem("jwt", jwt.jwt);
      console.log(localStorage.getItem("jwt"))
      navigate(`/userpage`);
    }
  }

  return (
    <div className="login">
      <h1>PLease type your name and password</h1>
      <form>
        <div>
          {showMessage ? (
            <p style={{ color: "red" }}>Incorrect password or user!</p>
          ) : null}
          <label htmlFor="username">
            {" "}
            Username:
            <input
              type="text"
              name="username"
              id="username"
              value={username}
              onChange={handleNameChange}
            />
          </label>
        </div>
        <div>
          <label htmlFor="password">
            {" "}
            Password:
            <input
              type="password"
              name="password"
              id="password"
              value={password}
              onChange={handlePasswordChange}
            />
          </label>
        </div>
      </form>
      <div>
        <button onClick={() => navigate("/registration")}>Sign up</button>
        <button onClick={() => handleLogin()}>Log in</button>
      </div>
    </div>
  );
}

export default LoginForm;