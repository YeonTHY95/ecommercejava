import { LoaderFunctionArgs } from 'react-router-dom';
import { Product } from './typeDefinition';

export async function productsLoader() {
  const products: Product[] = [
    { id: 1, name: 'Product 1', price: 99.99, image: 'placeholder.jpg' },
    { id: 2, name: 'Product 2', price: 149.99, image: 'placeholder.jpg' },
  ];
  return { products };
}

export async function productDetailLoader({ params }: LoaderFunctionArgs) {
  const product: Product = {
    id: Number(params.id),
    name: 'Product Name',
    price: 99.99,
    description: 'Detailed product description goes here.',
    image: 'placeholder.jpg',
  };
  return { product };
}