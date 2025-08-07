import { useQuery } from 'react-query';
import { fetchAllOrders } from '../api/orders';
import { Order } from '../interfaces/Interfaces';

export const useOrders = (options = {}) => {
  return useQuery<Order[], Error>('orders', fetchAllOrders, {
    staleTime: 300000,
    refetchOnWindowFocus: false,
    ...options,
  });
};
