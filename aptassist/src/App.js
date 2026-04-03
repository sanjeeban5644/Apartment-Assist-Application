import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Sidebar from "./components/Sidebar";
import Header from "./components/Header";

import Home from "./pages/Home";
import AddUser from "./pages/AddUser";

function App() {
  return (
    <Router>

      <Header />

      <div style={{ display: "flex" }}>

        <Sidebar />

        <div
          style={{
            flex: 1,
            padding: "25px",
            background: "#f8f9fa",
            minHeight: "calc(100vh - 56px)"
          }}
        >
          <Routes>

            <Route path="/" element={<Home />} />

            <Route path="/add-user" element={<AddUser />} />

          </Routes>

        </div>

      </div>

    </Router>
  );
}

export default App;