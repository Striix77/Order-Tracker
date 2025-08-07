import { Link } from 'react-router-dom';
import { List, ListItem } from '@mui/material';
import { useOrders } from '../hooks/useOrders';
import './OrdersPage.css';

function OrdersPage() {
  const { data: orders, error, isLoading } = useOrders();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div className="orders-container">
      <h1>Orders</h1>
      <List>
        <div className="list-container">
          {orders?.map((order) => (
            <ListItem key={order.id} className="list-item">
              <div className="list-item-container">
                <Link to={`/${order.id}`} style={{ textDecoration: 'none', color: 'inherit', width: '100%' }}>
                  <div className="order-details">
                    <span>OrderId: {order.id}</span>
                    <span>Status: {order.status}</span>
                  </div>
                </Link>
              </div>
            </ListItem>
          ))}
        </div>
      </List>
    </div>
  );
}

export default OrdersPage;
