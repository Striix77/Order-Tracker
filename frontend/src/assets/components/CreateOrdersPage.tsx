import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { SelectChangeEvent } from '@mui/material/Select';
import axios from 'axios';
import { Typography, TextField, FormControl, InputLabel, Select, MenuItem, Button } from '@mui/material';
import { useQueryClient } from 'react-query';
import { Order } from '../interfaces/Interfaces';
import { useOrders } from '../hooks/useOrders';
import './CreateOrdersPage.css';

const initialOrderState: Order = {
  id: 0,
  buyerId: 0,
  dateOfOrder: '',
  dateOfDelivery: '',
  status: '',
  total: 0,
};

function textFields(
  order: Order,
  handleChange: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void,
) {
  return (
    <>
      <TextField
        className="form-element"
        label="Buyer ID"
        name="buyerId"
        value={order.buyerId}
        onChange={handleChange}
        fullWidth
        margin="normal"
        required
        sx={{ color: 'rgba(237, 237, 237, 0.87)' }}
      />
      <TextField
        className="form-element"
        label="Date of Order"
        name="dateOfOrder"
        type="date"
        value={order.dateOfOrder}
        onChange={handleChange}
        fullWidth
        margin="normal"
        InputLabelProps={{ shrink: true }}
        required
        sx={{ color: 'rgba(237, 237, 237, 0.87)' }}
      />
      <TextField
        className="form-element"
        label="Date of Delivery"
        name="dateOfDelivery"
        type="date"
        value={order.dateOfDelivery}
        onChange={handleChange}
        fullWidth
        margin="normal"
        InputLabelProps={{ shrink: true }}
        required
        sx={{ color: 'rgba(237, 237, 237, 0.87)', marginBottom: '1rem' }}
      />
    </>
  );
}

function CreateOrdersPage() {
  const [order, setOrder] = useState<Order>(initialOrderState);
  const [error, setError] = useState<string | null>('');
  const [putOrPost, setPutOrPost] = useState<boolean>(false);
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const [fetchOrders, setFetchOrders] = useState<boolean>(false);
  const { data: orders, refetch, isStale } = useOrders({ enabled: fetchOrders });
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  useEffect(() => {
    if (fetchOrders && isStale) {
      refetch();
    }
  }, [fetchOrders, refetch, isStale]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement> | SelectChangeEvent<string>) => {
    const { name, value } = e.target;
    setOrder({
      ...order,
      [name]: value,
    });
  };

  const handleFormTypeChange = (e: SelectChangeEvent<string>) => {
    const { value } = e.target;
    setPutOrPost(value === 'put');
    if (value === 'post') {
      setOrder(initialOrderState);
      setFetchOrders(false);
    } else {
      setOrder(orders ? orders[order.id] : initialOrderState);
      setFetchOrders(true);
    }
  };

  const handleIdChange = (e: SelectChangeEvent<string>) => {
    const { value } = e.target;
    setOrder(orders ? orders.find((ord) => ord.id === parseInt(value, 10)) || initialOrderState : initialOrderState);
  };

  const handlePostSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError(null);
    let id = 0;
    try {
      const response = await axios.post('http://localhost:8080/', order);
      console.log(response.data);
      setOrder(initialOrderState);
      id = response.data;
    } catch (err) {
      setError('Failed to create order');
    } finally {
      setIsSubmitting(false);
      if (!error) {
        queryClient.invalidateQueries('orders');
        navigate(`/${id}`);
      }
    }
  };

  const handlePutSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError(null);

    try {
      const response = await axios.put(`http://localhost:8080/${order.id}`, order);
      console.log(response.data);
      setOrder(initialOrderState);
    } catch (err) {
      setError('Failed to modify order');
    } finally {
      setIsSubmitting(false);
      if (!error) {
        queryClient.invalidateQueries(['order', order.id]);
        queryClient.invalidateQueries('orders');
        navigate(`/${order.id}`);
      }
    }
  };

  return (
    <div className="form-container">
      <div className="form-header">
        <h1>
          {!putOrPost && 'Create Order'}
          {putOrPost && 'Modify Order'}
        </h1>
        <Select
          name="postOrPut"
          fullWidth
          className="form-element"
          value={putOrPost ? 'put' : 'post'}
          onChange={handleFormTypeChange}
          required
          style={{ color: 'rgba(237, 237, 237, 0.87)' }}
        >
          <MenuItem value="post">Create</MenuItem>
          <MenuItem value="put">Modify</MenuItem>
        </Select>
      </div>
      <form onSubmit={putOrPost ? handlePutSubmit : handlePostSubmit}>
        <div className="form-elements">
          {putOrPost && (
            <div className="selected-id-container">
              <FormControl className="form-control" fullWidth required>
                <InputLabel>Order ID</InputLabel>
                <Select
                  name="id"
                  className="form-element"
                  sx={{ color: 'rgba(237, 237, 237, 0.87)' }}
                  value={String(order.id)}
                  onChange={handleIdChange}
                  label="Order ID"
                  required
                >
                  <MenuItem value="">
                    <em>Select Order</em>
                  </MenuItem>
                  {orders?.map((ord) => (
                    <MenuItem key={ord.id} value={String(ord.id)}>
                      {ord.id}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </div>
          )}
          {textFields(order, handleChange)}
          <FormControl className="form-control" fullWidth required>
            <InputLabel>Status</InputLabel>
            <Select
              name="status"
              sx={{ color: 'rgba(237, 237, 237, 0.87)' }}
              className="form-element"
              value={order.status}
              onChange={handleChange}
              label="Status"
            >
              <MenuItem value="">
                <em>Select Status</em>
              </MenuItem>
              <MenuItem value="Pending">Pending</MenuItem>
              <MenuItem value="Shipped">Shipped</MenuItem>
              <MenuItem value="Delivered">Delivered</MenuItem>
              <MenuItem value="Cancelled">Cancelled</MenuItem>
            </Select>
          </FormControl>
          <TextField
            className="form-element"
            label="Total"
            name="total"
            type="number"
            value={order.total}
            onChange={handleChange}
            fullWidth
            margin="normal"
            required
            sx={{ color: 'rgba(237, 237, 237, 0.87)' }}
          />
          <Button
            className="submit-button"
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            disabled={isSubmitting}
            sx={{
              borderRadius: '7px',
              background: 'linear-gradient(145deg, #142732, #11202a)',
              boxShadow: '5px 5px 10px #0e1a22, -5px -5px 10px #182e3c',
            }}
          >
            {isSubmitting ? 'Submitting...' : 'Submit'}
          </Button>
          {error && (
            <Typography color="error" variant="body2">
              {error}
            </Typography>
          )}
        </div>
      </form>
    </div>
  );
}

export default CreateOrdersPage;
