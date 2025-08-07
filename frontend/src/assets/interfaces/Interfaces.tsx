export interface Order {
  id: number;
  buyerId: number;
  dateOfOrder: string;
  dateOfDelivery: string;
  status: string;
  total: number;
}
