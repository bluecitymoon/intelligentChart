(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('ProxyServer', ProxyServer);

    ProxyServer.$inject = ['$resource', 'DateUtils'];

    function ProxyServer ($resource, DateUtils) {
        var resourceUrl =  'api/proxy-servers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastValidationDate = DateUtils.convertDateTimeFromServer(data.lastValidationDate);
                        data.lastSuccessDate = DateUtils.convertDateTimeFromServer(data.lastSuccessDate);
                        data.lastFailDate = DateUtils.convertDateTimeFromServer(data.lastFailDate);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
