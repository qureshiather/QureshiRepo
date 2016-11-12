import binascii
import socket
import struct
import sys
import hashlib



#will return true if not corrupt, and false if corrupt
def checkCorrupt(bytes):
    UDP_Packet = unpacker.unpack(bytes)
    #Create the Checksum for comparison
    values = (UDP_Packet[0],UDP_Packet[1],UDP_Packet[2])
    packer = struct.Struct('I I 8s')
    packed_data = packer.pack(*values)
    chksum =  bytes(hashlib.md5(packed_data).hexdigest(), encoding="UTF-8")
    #Compare Checksums to test for corrupt data
    if UDP_Packet[3] == chksum:
        return true
    else:
        return false

#will return 0 or 1 depending on sequence
def checkSequence(bytes):
    UDP_Packet = unpacker.unpack(bytes)
    return UDP_Packet[0]

#Build ACK Packet
def buildACK(int):
    values = (1,int,'TestData',chksum)
    UDP_Packet_Data = struct.Struct('I I 8s 32s')
    UDP_Packet = UDP_Packet_Data.pack(*values)
    return UDP_Packet

#main
UDP_IP = "127.0.0.1"
UDP_PORT = 5005
dest = (UDP_IP, UDP_PORT)
unpacker = struct.Struct('I I 8s 32s')

#Create the socket and listen
sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
sock.bind((UDP_IP, UDP_PORT))

while True:
    #Receive Data
    data, addr = sock.recvfrom(1024) # buffer size is 1024 bytes
    UDP_Packet = unpacker.unpack(data)
    print("received from:", addr)
    print("received message:", UDP_Packet)

    if checkCorrupt(data) == true:
        sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
        sock.sendto(buildACK(UDP_Packet[1]), dest)
    else:
        sock = socket.socket(socket.AF_INET, # Internet
                    socket.SOCK_DGRAM) # UDP
        sock.sendto(buildACK(UDP_Packet[1] - 1), dest)

