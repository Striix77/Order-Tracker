import axios from 'axios';
import { Order } from '../interfaces/Interfaces';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

export const fetchOrderById = async (id: number): Promise<Order> => {
  const response = await api.get<Order>(`/${id}`);
  return response.data;
};
