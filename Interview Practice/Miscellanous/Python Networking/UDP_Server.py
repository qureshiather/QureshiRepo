# coding: utf-8
import binascii
import socket
import struct
import sys
import hashlib


#will return true if not corrupt, and false if corrupt
def checkCorrupt(data):
	UDP_Packet = unpacker.unpack(data)
	#Create the Checksum for comparison
	values = (UDP_Packet[0],UDP_Packet[1],UDP_Packet[2])
	packer = struct.Struct('I I 8s')
	packed_data = packer.pack(*values)
	chksum = bytes(hashlib.md5(packed_data).hexdigest())
	#Compare Checksums to test for corrupt data
	if UDP_Packet[3] == chksum:
		return True
	else:
		return False

#will return 0 or 1 depending on sequence
def checkSequence(data):
	UDP_Packet = unpacker.unpack(data)
	return UDP_Packet[0]

#Build ACK Packet
def buildACK(seq):
	values = (1, seq, 'TestData')
	UDP_Data = struct.Struct('I I 8s')
	packed_data = UDP_Data.pack(*values)
	#creates checksum based on values 
	chksum = bytes(hashlib.md5(packed_data).hexdigest())
	#now new value with chksum attached
	values = (1,seq,'TestData',chksum)
	UDP_Packet_Data = struct.Struct('I I 8s 32s')
	UDP_Packet = UDP_Packet_Data.pack(*values)
	return UDP_Packet

#OUR IP INFORMATION
UDP_IP = "127.0.0.1"
UDP_PORT = 4000

unpacker = struct.Struct('I I 8s 32s')

#Create UDP socket to send and receive
send_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
recv_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

#listen to this socket
recv_sock.bind((UDP_IP, UDP_PORT))

#CurrentSEQ only changes when it receives a non-corrupt packet
currentSeq = 0

#Server awaiting
while True:

	#Socket is ready to receive from client
	print ("Waiting for Client")
	data, addr = recv_sock.recvfrom(1024) 
	print (addr)

	#packet received, unpack and check sequence bit
	UDP_Packet = unpacker.unpack(data)
	packet_seq = checkSequence(data)

	#check if Data is corrupt, will enter if block if NOT corrupt
	if checkCorrupt(data) == True and packet_seq == currentSeq:

		#Print the received correct data
		print ("received from: ", addr)
		print ("received message:", UDP_Packet)

		#Send ACK packet back with same sequence bit as what was received (we good)
		recv_sock.sendto(buildACK(currentSeq), addr)

		#Flip the sequence bit, to get ready for the next packet from client
		if currentSeq == 0:
			currentSeq = 1
		else:
			currentSeq = 0

	#Corrupt packet or wrong Sequence bit
	else:
		#SEND A ACK with the other sequence bit
		if currentSeq == 0:
			currentSeq = 1
		else:
			currentSeq = 0
		recv_sock.sendto(buildACK(currentSeq), addr)