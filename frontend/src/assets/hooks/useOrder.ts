import { useQuery } from 'react-query';
import { Order } from '../interfaces/Interfaces';
import { fetchOrderById } from '../api/order';

export const useOrder = (id: number) => {
  return useQuery<Order, Error>(['order', id], () => fetchOrderById(id), {
    staleTime: 300000,
    refetchOnWindowFocus: false,
  });
};
