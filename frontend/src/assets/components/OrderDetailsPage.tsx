import { useNavigate, useParams } from 'react-router-dom';
import './OrderDetailsPage.css';
import { Button } from '@mui/material';
import axios from 'axios';
import { useState } from 'react';
import { useQueryClient } from 'react-query';
import { useOrder } from '../hooks/useOrder';

function OrderDetailsPage() {
  const { id } = useParams<{ id: string }>();
  const { data: order, error, isLoading } = useOrder(Number(id));
  const [deleteError, setDeleteError] = useState<string | null>('');
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error || deleteError) {
    return (error && <div>Error: {error.message}</div>) || (deleteError && <div>Error: {deleteError}</div>);
  }

  const handleDelete = async (orderId: number) => {
    console.log('Delete order with id: ', id);
    try {
      await axios.delete(`http://localhost:8080/${orderId}`);
    } catch (err) {
      console.log(err);
      setDeleteError('Failed to delete order!');
    } finally {
      queryClient.invalidateQueries('orders');
      queryClient.invalidateQueries(['order', orderId]);
      navigate('/');
    }
  };

  return (
    <div className="item-container">
      <h1>Order Details</h1>
      <div className="order-details">
        <span>OrderId: {order?.id}</span>
        <span>BuyerId: {order?.buyerId}</span>
        <span>Date of Order: {order?.dateOfOrder}</span>
        <span>Date Of Delivery: {order?.dateOfDelivery}</span>
        <span>Status: {order?.status}</span>
        <span>Total: {order?.total}</span>
        <div className="order-buttons-container">
          <Button
            sx={{
              borderRadius: '7px',
              background: 'linear-gradient(145deg, #142732, #11202a)',
              boxShadow: '5px 5px 10px #0e1a22, -5px -5px 10px #182e3c',
            }}
            className="detail-buttons"
            variant="contained"
            color="primary"
            onClick={() => navigate('/create')}
          >
            Edit
          </Button>
          <Button
            sx={{
              borderRadius: '7px',
              background: 'linear-gradient(145deg, #142732, #11202a)',
              boxShadow: '5px 5px 10px #0e1a22, -5px -5px 10px #182e3c',
            }}
            className="detail-buttons"
            variant="contained"
            color="primary"
            onClick={() => {
              if (order?.id !== undefined) {
                handleDelete(order.id);
              }
            }}
          >
            Delete
          </Button>
        </div>
      </div>
    </div>
  );
}

export default OrderDetailsPage;
