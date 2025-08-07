import axios from 'axios';
import { Order } from '../interfaces/Interfaces';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

export const fetchAllOrders = async (): Promise<Order[]> => {
  const response = await api.get<Order[]>('');
  return response.data;
};
