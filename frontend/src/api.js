import { toast } from 'react-hot-toast';

export const API_BASE_URL = 'http://localhost:8080';   elsewhere

export async function login(email, password) {
  const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });
  if (!response.ok) {
    throw new Error('Invalid credentials');
  }
  return response.json(); 
}

export async function register(fullName, email, password) {
  const response = await fetch(`${API_BASE_URL}/api/users/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ fullName, email, password }),
  });
  if (!response.ok) {
    throw new Error('Registration failed');
  }
  return response.json();
} 

toast.success('Added to cart!');