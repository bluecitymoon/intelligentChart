(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonNetworkTexiActivityDialogController', PersonNetworkTexiActivityDialogController);

    PersonNetworkTexiActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonNetworkTexiActivity', 'Person', 'NetworkTexiCompany'];

    function PersonNetworkTexiActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonNetworkTexiActivity, Person, NetworkTexiCompany) {
        var vm = this;

        vm.personNetworkTexiActivity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query();
        vm.networktexicompanies = NetworkTexiCompany.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personNetworkTexiActivity.id !== null) {
                PersonNetworkTexiActivity.update(vm.personNetworkTexiActivity, onSaveSuccess, onSaveError);
            } else {
                PersonNetworkTexiActivity.save(vm.personNetworkTexiActivity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personNetworkTexiActivityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
