import { useState } from 'react'
import { useEffect } from 'react'
import { useNavigate } from 'react-router'

function UserPage() {
 const [city, setCity] = useState("")
 const [date, setDate] = useState("")
 const [sunrise, setSunrise] = useState("")
 const [sunset, setSunSet] = useState("");
 const [showMessage, setShowMessage] = useState(false);
 const navigate = useNavigate();

 useEffect(() => {
    async function checkAccess() {
        const jwt = {
            jwt: localStorage.getItem("jwt")
        }
        const response = await fetch('api/suncalculator', {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("jwt")
            },
            //body: JSON.stringify( jwt ),
        });
        if (!response.ok) {
         navigate("/")
        }
    }
    checkAccess();
  }, [sunrise]);

     async function postCityAndDate(e) {
        e.preventDefault();
        try{
            const response = await fetch(`api/suncalculator?city=${city}&date=${date}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + localStorage.getItem("jwt")
                },
                //body: JSON.stringify( city, date ),
            });

            if (response.ok) {
                const sundata = await response.json()
                console.log(sundata.sunrise )
                setSunSet(sundata.sunset)
                setSunrise(sundata.sunrise)
            }

        } catch (error){
            console.error( error.message + " - Please choose another trainer name.")
        }
    }

 
 function handleCityChange(e) {
    setCity(e.target.value)
}

function handleDateChange(e) {
    setDate(e.target.value)
}
console.log(sunrise)
  return (
    <div>
    <h1>Please type the city in:</h1>
    <form onSubmit={postCityAndDate}>
        <div>
            {showMessage ? <p style={{ color: 'red' }}>!</p> : null}
            <label htmlFor="city"> City:
                <input type="text"
                    name="city"
                    id="city"
                    value={city}
                    onChange={handleCityChange}
                />
            </label>
        </div>
        <div>
            {showMessage ? <p style={{ color: 'red' }}>!</p> : null}
            <label htmlFor="date"> Date:
                <input type="text"
                    name="date"
                    id="date"
                    value={date}
                    onChange={handleDateChange}
                />
            </label>
            <br></br>
            <label>Sunrise: </label>
              <input className="e-input" type="text" placeholder="sunrise" value={sunrise} readOnly={true}/>
        <br></br>
        <label>Sunset: </label>
        <input className="e-input" type="text" placeholder="sunset" value={sunset} readOnly={true}/>
        </div>
        <button>Get City data</button>
    </form>
</div>
  )
}

export default UserPage