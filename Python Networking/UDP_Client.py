# coding: utf-8
import binascii
import socket
import struct
import sys
import hashlib

#given a packet, check if it's corrupt (using checkSum) 
def checkCorrupt(data):
	UDP_Packet = unpacker.unpack(data)
	#Create the Checksum for comparison
	values = (UDP_Packet[0],UDP_Packet[1],UDP_Packet[2])
	packer = struct.Struct('I I 8s')
	packed_data = packer.pack(*values)
	chksum =  bytes(hashlib.md5(packed_data).hexdigest())
	#Compare Checksums to test for corrupt data
	if UDP_Packet[3] == chksum:
		return True
	else:
		return False

#Checks the sequence "Bit" of a packet returns it
def checkSequence(data):
	UDP_Packet = unpacker.unpack(data)
	return UDP_Packet[0]

#builds a pseudo UDP packet based on given Sequence number and String
#ACK – Indicates if packet is ACK or not
#SEQ – Sequence number of packet 
#DATA – Application Data (8 bytes)
#CHKSUM – MD5 Checksum of packet (32 Bytes)
def buildPacket(string, seq):
	values = (0, seq, string)
	UDP_Data = struct.Struct('I I 8s')
	packed_data = UDP_Data.pack(*values)
	#creates checksum based on values 
	chksum = bytes(hashlib.md5(packed_data).hexdigest())
	#now new value with chksum attached
	values = (0, seq, string, chksum)
	UDP_Packet_Data = struct.Struct('I I 8s 32s')
	return UDP_Packet_Data.pack(*values)

def sendPacket(string, seq):
	send_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	recv_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	
	recv_sock.bind(us) #this socket waits for ACK packets
	recv_sock.settimeout(9) #wait for 9s for ACK packet after recv is called

	ack_received = False

	while not ack_received:
		packet = buildPacket(string, seq)
		send_sock.sendto(packet, dest) #Send DATA to server

		try: 
			data, addr = recv_sock.recvfrom(1024) # buffer size is 1024 bytes
		except socket.timeout:
			print ("Packet timed out")
		else:
			print (data)
			ACK_seq = checkSequence(data)
			if 	checkCorrupt(data) and ACK_seq == seq:
				#break
				ack_received = True

	#Alternate the seq bit
	if seq == 0:
		seq = 1
	else:
		seq = 0

#MAIN FUNCTION

US_UDP_IP = "127.0.0.1"
US_UDP_PORT = 3500

UDP_IP = "127.0.0.1"
UDP_PORT = 4000

us = (US_UDP_IP, US_UDP_PORT)
dest = (UDP_IP, UDP_PORT)
unpacker = struct.Struct('I I 8s 32s')

print("UDP target IP:", UDP_IP)
print("UDP target port:", UDP_PORT)

#Sending 3 Packets
sendPacket("NCC-1701", 0)
sendPacket("NCC-1664", 1)
sendPacket("NCC-1017", 0)