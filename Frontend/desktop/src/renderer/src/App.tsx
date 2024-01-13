import { BrowserRouter, Routes, Route } from "react-router-dom"
import Login from "./pages/Login"

function App(): JSX.Element {
    return (
        <div className="min-w-screen min-h-screen">
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Login />} />
            </Routes>
          </BrowserRouter>
        </div>
    )
}

export default App
