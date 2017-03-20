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
        vm.people = Person.query();
        vm.paymenttools = PaymentTool.query();

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
