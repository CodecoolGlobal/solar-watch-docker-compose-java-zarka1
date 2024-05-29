import { useState } from 'react'
import { useNavigate } from 'react-router'

function Registration() {
 const [username, setUsername] = useState("")
 const [password, setPassword] = useState("")
 const [showPassword, setShowPassword] = useState(false);
 const [showMessage, setShowMessage] = useState(false);
 const navigate = useNavigate();

     async function postUser(e) {
        e.preventDefault();
        try{
            const response = await fetch('api/user/register', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                setShowMessage(true);
                throw new Error('Server error: ' + response.status);
            }

            navigate("/");
        } catch (error){
            console.error( error.message + " - Please choose another trainer name.")
        }
    }

 
 function handleNameChange(e) {
    setUsername(e.target.value)
}

function handlePasswordChange(e) {
    setPassword(e.target.value);
}

function togglePasswordVisibility() {
    setShowPassword(!showPassword);
}

  return (
    <div>
    <h1>Please registrate yourself</h1>
    <form onSubmit={postUser}>
        <div>
            {showMessage ? <p style={{ color: 'red' }}>Unfortunately, this trainer name is already taken. Please choose another one!</p> : null}
            <label htmlFor="RegisterName"> Trainer name:
                <input type="text"
                    name="username"
                    id="RegisterName"
                    value={username}
                    onChange={handleNameChange}
                />
            </label>
        </div>
        <div>
            <label htmlFor="RegisterPassword"> Password:
                <input type={showPassword ? 'text' : 'password'}
                    name="password"
                    id="RegisterPassword"
                    value={password}
                    onChange={handlePasswordChange}
                />
            </label>
            <button type="button" onClick={togglePasswordVisibility}>
                {showPassword ? 'Hide' : 'Show'} Password
            </button>
        </div>
        <button>Sign up</button>
    </form>
</div>
  )
}

export default Registration