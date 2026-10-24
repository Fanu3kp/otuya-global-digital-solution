export interface Order {
  id: number;
  title: string;
  thumbnail?: string;
  quantity: number;
  totalPrice: number;
  status: string;
  contactPhone?: string;
  contactEmail?: string;
  contactAddress?: string;
  itemDescription?: string;
  createdAt: string;
}

export interface OrderRequest {
  packageId: number;
  quantity: number;
  contactPhone: string;
  contactEmail: string;
  contactAddress: string;
  itemDescription: string;
}
