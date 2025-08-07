import { Route, BrowserRouter, Routes } from 'react-router-dom';
import './App.css';
import OrdersPage from './assets/components/OrdersPage';
import CreateOrdersPage from './assets/components/CreateOrdersPage';
import Navbar from './assets/components/Navbar';
import OrderDetailsPage from './assets/components/OrderDetailsPage';

function App() {
  return (
    <BrowserRouter>
      <div className="app-container">
        <Navbar />
        <Routes>
          <Route path="/" element={<OrdersPage />} />
          <Route path="/create" element={<CreateOrdersPage />} />
          <Route path="/:id" element={<OrderDetailsPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
