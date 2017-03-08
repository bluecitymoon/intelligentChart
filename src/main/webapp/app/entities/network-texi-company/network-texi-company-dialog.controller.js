(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('NetworkTexiCompanyDialogController', NetworkTexiCompanyDialogController);

    NetworkTexiCompanyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NetworkTexiCompany'];

    function NetworkTexiCompanyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NetworkTexiCompany) {
        var vm = this;

        vm.networkTexiCompany = entity;
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
            if (vm.networkTexiCompany.id !== null) {
                NetworkTexiCompany.update(vm.networkTexiCompany, onSaveSuccess, onSaveError);
            } else {
                NetworkTexiCompany.save(vm.networkTexiCompany, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:networkTexiCompanyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
