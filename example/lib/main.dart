import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:gf_sdk/gf_sdk.dart';
import 'package:web3dart/credentials.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final String _platformVersion = 'Unknown';
  final _gfSdkPlugin = GfSdk();
  String authKey = "";
  String creator = "";
  String spAddress = "";
  String responseApproval = "";
  String responseCreateBucket = "";
  String accountInfo = "";
  String accountBalance = "";
  String stats = "";
  String userBuckets = "";
  String bucketInfo = "";
  String bucketObjects = "";
  String storageProviders = "";

  @override
  void initState() {
    super.initState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    final creds = EthPrivateKey.fromHex(authKey);
    setState(() {
      creator = creds.address.hex;
    });
  }

  createBucketApproval() {

    print("authKey: $authKey, primaryAddress: $creator, spAddress: $spAddress, bucketName: test-bucket");
    _gfSdkPlugin.getApproval(
      authKey: authKey, primaryAddress: creator, spAddress: spAddress, bucketName: "test-bucket"
    ).then((value) {
      setState(() {
        responseApproval = value!;
      });
    });
  }

  createBucket() {
    _gfSdkPlugin.createBucket(
      authKey: authKey, primaryAddress: creator, spAddress: spAddress, bucketName: "bscdy"
    ).then((value) {
      setState(() {
        responseCreateBucket = value!;
      });
    });
  }

  getStorageProviders() {
    _gfSdkPlugin.getStorageProviders().then((value) {
      setState(() {
        storageProviders = value!;
      });
    });
  }

  getAccountInfo() {
    _gfSdkPlugin.getAccountInfo(address: creator).then((value) {
      setState(() {
        accountInfo = value!;
      });
    });
  }

  getAccountBalance() {
    _gfSdkPlugin.getAccountBalance(address: creator).then((value) {
      setState(() {
        accountBalance = value!;
      });
    });
  }

  getStats() {
    _gfSdkPlugin.getStats().then((value) {
      setState(() {
        stats = value!;
      });
    });
  }

  getUserBuckets() {
    _gfSdkPlugin.getUserBuckets(address: creator, spAddress: spAddress).then((value) {
      setState(() {
        userBuckets = value!;
      });
    });
  }

  getBucketInfo() {
    _gfSdkPlugin.getBucketInfo(bucketName: "test-bucket").then((value) {
      setState(() {
        bucketInfo = value!;
      });
    });
  }

  getBucketObjects() {
    _gfSdkPlugin.getBucketObjects(bucketName: "test-bucket").then((value) {
      setState(() {
        bucketObjects = value!;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const SizedBox(height: 20),
              // input field for auth key and with update button to call initPlatformState
              Row(
                children: [
                  Expanded(
                    child: TextField(
                      onChanged: (value) {
                        setState(() {
                          authKey = value;
                        });
                      },
                      decoration: const InputDecoration(
                        border: OutlineInputBorder(),
                        labelText: 'Auth Key',
                      ),
                    ),
                  ),
                  TextButton(onPressed: (){
                    initPlatformState();
                  }, child: const Text("Update")),
                ],
              ),
              const SizedBox(height: 20),
              // input field for sp address
              Row(
                children: [
                  Expanded(
                    child: TextField(
                      onChanged: (value) {
                        setState(() {
                          spAddress = value;
                        });
                      },
                      decoration: const InputDecoration(
                        border: OutlineInputBorder(),
                        labelText: 'Sp Address',
                      ),
                    ),
                  ),
                ],
              ),

              // display the creator and SP address
              const SizedBox(height: 20),
              Text("Creator: $creator"),
              Text("SP Address: $spAddress"),
              const SizedBox(height: 20),
              Center(
                child: Text('Running on: $_platformVersion\n'),
              ),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                createBucketApproval();
              }, child: const Text("Create Bucket Approval")),
              Text("Create Bucket Approval Response $responseApproval"),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                createBucket();
              }, child: const Text("Create Bucket")),
              Text("Create Bucket Response $responseCreateBucket"),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                getStorageProviders();
              }, child: const Text("get sp providers")),
              Text("get sp providers Response $storageProviders"),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                getAccountInfo();
              }, child: const Text("get account info")),
              Text("get account info Response $accountInfo"),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                getAccountBalance();
              }, child: const Text("get account balance")),
              Text("get account balance Response $accountBalance"),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                getStats();
              }, child: const Text("get stats")),
              Text("get stats Response $stats"),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                getUserBuckets();
              }, child: const Text("get user buckets")),
              Text("get user buckets Response $userBuckets"),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                getBucketInfo();
              }, child: const Text("get bucket info")),
              Text("get bucket info Response $bucketInfo"),
              const SizedBox(height: 20),
              TextButton(onPressed: (){
                getBucketObjects();
              }, child: const Text("get bucket objects")),
              Text("get bucket objects Response $bucketObjects"),

            ],
          ),
        ),
      ),
    );
  }
}
