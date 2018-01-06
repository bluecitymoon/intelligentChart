(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanPaymentToolDialogController', PersonFanPaymentToolDialogController);

    PersonFanPaymentToolDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonFanPaymentTool', 'Person', 'PaymentTool'];

    function PersonFanPaymentToolDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonFanPaymentTool, Person, PaymentTool) {
        var vm = this;

        vm.personFanPaymentTool = entity;
        vm.clear = clear;
        vm.save = save;
                vm.people = [];
        vm.searchPersonWithKeyword = searchPersonWithKeyword;
        function searchPersonWithKeyword(keyword) {

            if (keyword) {

                Person.loadAllByPersonName({
                    page: 0,
                    size: 10,
                    name: keyword
                }, onSuccess, onError);
            }

            function onSuccess(data) {
                vm.people = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        vm.paymenttools = PaymentTool.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personFanPaymentTool.id !== null) {
                PersonFanPaymentTool.update(vm.personFanPaymentTool, onSaveSuccess, onSaveError);
            } else {
                PersonFanPaymentTool.save(vm.personFanPaymentTool, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personFanPaymentToolUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
