(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonTaxiActivity', PersonTaxiActivity);

    PersonTaxiActivity.$inject = ['$resource', 'DateUtils'];

    function PersonTaxiActivity ($resource, DateUtils) {
        var resourceUrl =  'api/person-taxi-activities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-taxi-activities/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();
