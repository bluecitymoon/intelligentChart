(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PaymentToolDialogController', PaymentToolDialogController);

    PaymentToolDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PaymentTool'];

    function PaymentToolDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PaymentTool) {
        var vm = this;

        vm.paymentTool = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.paymentTool.id !== null) {
                PaymentTool.update(vm.paymentTool, onSaveSuccess, onSaveError);
            } else {
                PaymentTool.save(vm.paymentTool, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:paymentToolUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
