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
        return True
    else:
        return False

#will return 0 or 1 depending on sequence
def checkSequence(bytes):
    UDP_Packet = unpacker.unpack(bytes)
    return UDP_Packet[0]

#Build ACK Packet
def buildACK(seq):
    values = (1,seq,'TestData',chksum)
    UDP_Packet_Data = struct.Struct('I I 8s 32s')
    UDP_Packet = UDP_Packet_Data.pack(*values)
    return UDP_Packet


UDP_IP = "127.0.0.1"
UDP_PORT = 4000

#Create UDP socket to send and receive
send_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
recv_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

#listen to this socket
recv_sock.bind((UDP_IP, UDP_PORT))

currentSeq = 0

while True:
    data, addr = recv_sock.recvfrom(1024) # buffer size is 1024 bytes
    print ("Waiting for Client")

    UDP_Packet = unpacker.unpack(data)
    packet_seq = checkSequence(data)

    if(checkCorrupt(data)):
        sock.sendto(buildACK(packet_seq, dest))
        if packet_seq == currentSeq:
            print ("received from: ", addr)
            print("received message:", UDP_Packet)
            if currentState == 0:
                currentState = 1
            else:
                currentState = 0
    else:
        neg_seq = str(1 - currentSeq)
        sock.sendto(buildACK(neg_seq), dest)